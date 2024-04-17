package com.Faceit.Faceit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.faceit.faceit.dao.PlayerRepository;
import com.faceit.faceit.dao.UserRepository;
import com.faceit.faceit.exception.PlayerNotFoundException;
import com.faceit.faceit.model.entity.Player;
import com.faceit.faceit.model.entity.User;
import com.faceit.faceit.service.UserService;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock private PlayerRepository playerRepository;
  @Mock private UserRepository userRepository;
  @Mock private CacheManager cacheManager;
  
  @InjectMocks private UserService userService;

  @Test
  void testLoadUserByUsername_ExistingUser() {
    String username = "testUser";
    User mockUser = new User();
    mockUser.setUsername(username);
    when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(mockUser));
    assertEquals(username, userService.loadUserByUsername(username).getUsername());
  }

  @Test
  void testLoadUserByUsername_UserNotFound() {
    String username = "nonExistentUser";
    when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());
    assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
  }

  @Test
  void testGetUserByName_ExistingUser() {
    String username = "testUser";
    User mockUser = new User();
    mockUser.setUsername(username);

    when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(mockUser));
    User result = userService.getUserByName(username);
    assertEquals(mockUser, result);
  }

  @Test
  void testGetUserByName_UserNotFound() {
    String username = "nonExistentUser";
    when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> userService.getUserByName(username));
  }

  @Test
  void testDeleteUser_WhenUserExists() {
    User user = new User();
    String username = "testUser";
    user.setUsername("testUser");
    when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));
    userService.deleteUser(username);
    verify(userRepository, times(1)).delete(user);
  }

  @Test
  void testDeleteUser_WhenUserDoesNotExist() {
    String username = "nonExistentUser";
    when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());
    assertThrows(
        UsernameNotFoundException.class,
        () -> {
          userService.deleteUser(username);
        });
  }

  @Test
  void testGetAllUsers() {
    List<User> mockUsers = Collections.singletonList(new User());
    when(userRepository.findAll()).thenReturn(mockUsers);
    assertEquals(mockUsers, userService.getAllUsers());
  }

  @Test
  void testGetFavoritePlayersByUsername_WhenUserExists() {
    String username = "testUser";
    User mockUser = new User();
    Set<Player> favoritePlayers = new HashSet<>();
    favoritePlayers.add(new Player());
    mockUser.setFavoritePlayers(favoritePlayers);
    when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(mockUser));
    assertEquals(favoritePlayers, userService.getFavoritePlayersByUsername(username));
  }

  @Test
  void testGetFavoritePlayersByUsername_WhenUserDoesNotExist() {

    String username = "nonExistentUser";
    when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

    assertThrows(
        UsernameNotFoundException.class,
        () -> {
          userService.getFavoritePlayersByUsername(username);
        });
  }

  @Test
  void testAddPlayerToUser_NoSuchPlayer() {
    String username = "testUser";
    String nickname = "testPlayer";

    User user = new User();
    user.setUsername(username);
    when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));
    when(playerRepository.findPlayerByNickname(nickname)).thenReturn(Optional.empty());
    String result = userService.addPlayerToUser(username, nickname);
    assertEquals("Игрок с ником testPlayer был успешно добавлен", result);
  }
  @Test
  void testAddPlayerToUser_AlreadyExist() {
    String username = "testUser";
    String nickname = "testPlayer";
    Player player = new Player();
    User user = new User();

    Set<Player> favoritePlayers = new HashSet<>();
    favoritePlayers.add(player);
    user.setFavoritePlayers(favoritePlayers);
    user.setUsername(username);
    when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));
    when(playerRepository.findPlayerByNickname(nickname)).thenReturn(Optional.of(player));
    String result = userService.addPlayerToUser(username, nickname);
    assertEquals("Игрок с ником testPlayer был добавлен пользователю ранее", result);
  }

  @Test
  void testAddPlayerToUser_NoSuchUser() {
    String username = "nonExistentUser";
    String nickname = "testPlayer";

    when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());
    assertThrows(
        UsernameNotFoundException.class,
        () -> {
          userService.addPlayerToUser(username, nickname);
        });
    verify(playerRepository, never()).findPlayerByNickname(any());
    verify(userRepository, never()).save(any());
  }

  @Test
  void testRemovePlayer_AllExist() {
    String username = "testUser";
    String nickname = "testPlayer";

    User user = new User();
    user.setUsername(username);

    Player player = new Player();
    player.setNickname(nickname);

    user.getFavoritePlayers().add(player);

    when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));
    when(playerRepository.findPlayerByNickname(nickname)).thenReturn(Optional.of(player));

    userService.removePlayer(username, nickname);
    assertFalse(user.getFavoritePlayers().contains(player));
    verify(playerRepository, times(1)).delete(player);
  }

  @Test
  void testRemovePlayer_NoSuchUser() {
    String username = "nonExistentUser";
    String nickname = "testPlayer";

    when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

    assertThrows(
        UsernameNotFoundException.class,
        () -> {
          userService.removePlayer(username, nickname);
        });
    verify(playerRepository, never()).findPlayerByNickname(any());
    verify(userRepository, never()).save(any());
  }
  @Test
  void testSaveUserCache_CacheNull() {
    User user = new User();
    Cache cacheMock = mock(Cache.class);
    user.setUsername("testUser");
    when(cacheManager.getCache("data")).thenReturn(cacheMock);
    userService.saveUserCache(user);

    verify(cacheManager, times(1)).getCache("data");
    verify(cacheMock, times(1)).put(user.getUsername(), user);
  }


  @Test
  void testRemovePlayer_NoSuchPlayer() {
    String username = "testUser";
    String nickname = "nonExistentPlayer";
    User user = new User();

    when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));
    when(playerRepository.findPlayerByNickname(nickname)).thenReturn(Optional.empty());

    assertThrows(
        PlayerNotFoundException.class,
        () -> {
          userService.removePlayer(username, nickname);
        });
    verify(userRepository, never()).save(any());
  }

  @Test
  void testGetUsersByPlayer_WhenPlayerExists() {
    String nickname = "testPlayer";
    User user1 = new User();
    User user2 = new User();
    List<User> users = new ArrayList<>();
    users.add(user1);
    users.add(user2);

    when(userRepository.findUsersByFavoritePlayer(nickname)).thenReturn(Optional.of(users));

    List<User> result = userService.getUsersByPlayer(nickname);

    assertEquals(2, result.size());
    assertTrue(result.contains(user1));
    assertTrue(result.contains(user2));
  }

  @Test
  void testGetUsersByPlayer_WhenPlayerDoesNotExist() {
    String nickname = "nonExistentPlayer";
    when(userRepository.findUsersByFavoritePlayer(nickname)).thenReturn(Optional.empty());

    assertThrows(
        PlayerNotFoundException.class,
        () -> {
          userService.getUsersByPlayer(nickname);
        });
  }

  @Test
  void testAddManyPlayersToUser_1Exist_2No_UserExist() {
    String username = "testPlayer";
    String nickname1 = "s1mple";
    String nickname2 = "m0nesy";
    String nickname3 = "b1t";
    User user = new User();
    List<String> nicknames = new ArrayList<>();
    nicknames.add(nickname1);
    nicknames.add(nickname2);
    nicknames.add(nickname3);
    Player player2 = new Player();
    Set<Player> favoritePlayers = new HashSet<>();
    favoritePlayers.add(player2);
    user.setFavoritePlayers(favoritePlayers);

    when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));
    when(playerRepository.findPlayerByNickname(nickname1)).thenReturn(Optional.empty());
    when(playerRepository.findPlayerByNickname(nickname2)).thenReturn(Optional.of(player2));
    when(playerRepository.findPlayerByNickname(nickname3)).thenReturn(Optional.empty());
    when(playerRepository.save(any(Player.class))).thenAnswer(invocation -> {
      return invocation.getArgument(0);
    });

    String result = userService.addManyPlayersToUser(username, nicknames);
    assertTrue(result.contains("Игрок с ником " + nickname1 + " был успешно добавлен\n"));
    assertTrue(result.contains("Игрок с ником " + nickname2 + " был добавлен пользователю ранее"));
    assertTrue(result.contains("Игрок с ником " + nickname3 + " был успешно добавлен"));
    assertEquals(3, user.getFavoritePlayers().size());
    verify(userRepository, times(1)).save(user);
  }

  @Test
  void testAddManyPlayersToUser_UserNotFound() {
    String username = "testPlayer";
    String nickname1 = "s1mple";
    String nickname2 = "m0nesy";
    List<String> nicknames = new ArrayList<>();
    nicknames.add(nickname1);
    nicknames.add(nickname2);
    when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> userService.addManyPlayersToUser(username, nicknames));
  }
}
