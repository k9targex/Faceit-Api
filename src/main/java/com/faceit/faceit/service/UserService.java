package com.faceit.faceit.service;

import com.faceit.faceit.component.UserCache;
import com.faceit.faceit.dao.PlayerRepository;
import com.faceit.faceit.exception.PlayerNotFoundException;
import com.faceit.faceit.model.entity.Player;
import com.faceit.faceit.model.entity.User;
import com.faceit.faceit.dao.UserRepository;
import com.faceit.faceit.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PlayerRepository playerRepository;
    private UserCache userCache;
    private static final String USER_NOT_FOUND_MESSAGE = "Пользователя с именем \"%s\" не существует";
    private static final String PLAYER_NOT_FOUND_MESSAGE = "Игрока с ником \"%s\" не существует";

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }
    @Autowired
    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findUserByUsername(username).orElseThrow(()->new UsernameNotFoundException(
                String.format("User '%s' not found",username)));
        return UserDetailsImpl.build(user);
    }
    public User getUserByName(String username)throws UsernameNotFoundException {
        User cachedUser = userCache.get(username);
        if (cachedUser != null) {
            return cachedUser;
        } else {
            User user = userRepository.findUserByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));
            userCache.put(username, user);
            return user;
        }
    }
    public void  saveUser(User user){
        userRepository.save(user);
        userCache.put(user.getUsername(), user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
        userCache.remove(user.getUsername());
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByPlayer(String nickname) {
        return userRepository.findUsersByFavoritePlayer(nickname)
                .filter(users -> !users.isEmpty())
                .orElseThrow(() -> new PlayerNotFoundException(String.format(PLAYER_NOT_FOUND_MESSAGE, nickname)));
    }
    public void deleteUser(String username) {
        User userOptional = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE,username)));
            deleteUser(userOptional);
    }


    public Set<Player> getFavoritePlayersByUsername(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE,username)));
        return user.getFavoritePlayers();
    }


    public String addPlayerToUser(String username, String nickname) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new  UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE,username)));

        Player player = playerRepository.findPlayerByNickname(nickname)
                .orElseGet(() -> {
                    Player newPlayer = new Player();
                    newPlayer.setNickname(nickname);
                    return playerRepository.save(newPlayer);
                });

        Set<Player> favoritePlayers = user.getFavoritePlayers();
        if (favoritePlayers == null) {
            favoritePlayers = new HashSet<>();
        }else {
            if (favoritePlayers.contains(player)) {
                return "Игрок уже добавлен";
            }
        }
        favoritePlayers.add(player);
        user.setFavoritePlayers(favoritePlayers);
        saveUser(user);
        return "Игрок был успешно добавлен";
    }

    public void removePlayer(String username, String nickname) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new  UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE,username)));

        Player player = playerRepository.findPlayerByNickname(nickname)
                .orElseThrow(() ->new PlayerNotFoundException(String.format(PLAYER_NOT_FOUND_MESSAGE,nickname)));

        user.getFavoritePlayers().removeIf(favoritePlayers-> favoritePlayers.getNickname().equals(nickname));
        saveUser(user);
        player.getSubUsers().removeIf(savedUser -> savedUser.getUsername().equals(username));
        playerRepository.save(player);
        if(player.getSubUsers().isEmpty())
        {
            playerRepository.delete(player);
        }

    }
    public String getTokenFromRequest(String authorizationHeader){
        String token;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return token;
    }

}
