package com.faceit.faceit.service;

import com.faceit.faceit.dao.PlayerRepository;
import com.faceit.faceit.model.entity.Player;
import com.faceit.faceit.model.entity.User;
import com.faceit.faceit.dao.UserRepository;
import com.faceit.faceit.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PlayerRepository playerRepository;

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        } else {
            throw new IllegalArgumentException("User with username " + username + " not found");
        }
    }


    public Set<Player> getFavoritePlayersByUsername(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User with username " + username + " not found"));
        return user.getFavoritePlayers();
    }


    public void addPlayerToUser(String username, String nickname) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User with username " + username + " not found"));

        Player player = playerRepository.findPlayerByNickname(nickname)
                .orElseGet(() -> {
                    Player newPlayer = new Player();
                    newPlayer.setNickname(nickname);
                    return playerRepository.save(newPlayer);
                });
        if (user != null) {
            Set<Player> favoritePlayers = user.getFavoritePlayers();
            if (favoritePlayers == null) {
                favoritePlayers = new HashSet<>();
            }
            favoritePlayers.add(player);
            user.setFavoritePlayers(favoritePlayers);
            userRepository.save(user);
        }
    }

    public void removePlayer(String username, String nickname) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User with username " + username + " not found"));

        Player player = playerRepository.findPlayerByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("Player with nickname " + nickname + " not found"));

        if (user != null && player != null) {
            user.getFavoritePlayers().removeIf(favoritePlayers-> favoritePlayers.getNickname().equals(nickname));
            userRepository.save(user);
            player.getSubUsers().removeIf(savedUser -> savedUser.getUsername().equals(username));
            playerRepository.save(player);
            if(player.getSubUsers().isEmpty())
            {
                playerRepository.delete(player);
            }
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
