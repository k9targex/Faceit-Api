package com.faceit.faceit.service;

import com.faceit.faceit.dao.PlayerRepository;
import com.faceit.faceit.dao.UserRepository;
import com.faceit.faceit.exception.PlayerNotFoundException;
import com.faceit.faceit.model.entity.Player;
import com.faceit.faceit.model.entity.User;
import com.faceit.faceit.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@CacheConfig(cacheNames = "data")
public class UserService implements UserDetailsService {
  private static final String USER_NOT_FOUND_MESSAGE = "Пользователя с именем \"%s\" не существует";
  private static final String PLAYER_NOT_FOUND_MESSAGE = "Игрока с ником \"%s\" не существует";
  private final UserRepository userRepository;
  private final PlayerRepository playerRepository;
  private final CacheManager cacheManager;

  @Autowired
  public UserService(
      UserRepository userRepository, CacheManager cacheManager, PlayerRepository playerRepository) {
    this.userRepository = userRepository;
    this.cacheManager = cacheManager;
    this.playerRepository = playerRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findUserByUsername(username)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(String.format("User '%s' not found", username)));
    return UserDetailsImpl.build(user);
  }

  @CachePut()
  public void saveUserCache(User user) {
    Cache cache = cacheManager.getCache("data");
    if (cache != null) {
      cache.put(user.getUsername(), user);
    }
  }

  @CacheEvict(key = "#user.username")
  public void deleteUserCache(User user) {
    //just delete cache
  }

  @Cacheable
  public User getUserByName(String username) throws UsernameNotFoundException {
    return userRepository
        .findUserByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public List<User> getUsersByPlayer(String nickname) {
    return userRepository
        .findUsersByFavoritePlayer(nickname)
        .filter(users -> !users.isEmpty())
        .orElseThrow(
            () -> new PlayerNotFoundException(String.format(PLAYER_NOT_FOUND_MESSAGE, nickname)));
  }
  
  public void deleteUser(String username) {
    User userOptional =
        userRepository
            .findUserByUsername(username)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));
    userRepository.delete(userOptional);

    deleteUserCache(userOptional);
  }

  public Set<Player> getFavoritePlayersByUsername(String username) {
    User user =
        userRepository
            .findUserByUsername(username)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));
    return user.getFavoritePlayers();
  }

  public void removePlayer(String username, String nickname) {
    User user =
        userRepository
            .findUserByUsername(username)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));

    Player player =
        playerRepository
            .findPlayerByNickname(nickname)
            .orElseThrow(
                () ->
                    new PlayerNotFoundException(String.format(PLAYER_NOT_FOUND_MESSAGE, nickname)));

    user.getFavoritePlayers()
        .removeIf(favoritePlayers -> favoritePlayers.getNickname().equals(nickname));
    userRepository.save(user);
    saveUserCache(user);
    player.getSubUsers().removeIf(savedUser -> savedUser.getUsername().equals(username));
    playerRepository.save(player);
    if (player.getSubUsers().isEmpty()) {
      playerRepository.delete(player);
    }
  }

  public String addPlayerToUser(String username, String nickname) {
    User user =
        userRepository
            .findUserByUsername(username)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));

    Player player =
        playerRepository
            .findPlayerByNickname(nickname)
            .orElseGet(
                () -> {
                  Player newPlayer = new Player();
                  newPlayer.setNickname(nickname);
                  return playerRepository.save(newPlayer);
                });

    Set<Player> favoritePlayers = user.getFavoritePlayers();
    if (favoritePlayers == null) {
      favoritePlayers = new HashSet<>();
    } else {
      if (favoritePlayers.contains(player)) {
        return String.format("Игрок с ником %s был добавлен пользователю ранее ", nickname);
      }
    }
    favoritePlayers.add(player);
    user.setFavoritePlayers(favoritePlayers);
    userRepository.save(user);
    saveUserCache(user);

    return String.format("Игрок с ником %s был успешно добавлен", nickname);
  }
}
