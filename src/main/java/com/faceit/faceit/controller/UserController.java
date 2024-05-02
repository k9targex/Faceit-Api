package com.faceit.faceit.controller;

import com.faceit.faceit.model.entity.Player;
import com.faceit.faceit.model.entity.User;
import com.faceit.faceit.security.JwtCore;
import com.faceit.faceit.service.UserService;
import com.faceit.faceit.utilities.RequestCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private JwtCore jwtCore;
  private  RequestCounter requestCounter;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setJwtCore(JwtCore jwtCore) {
    this.jwtCore = jwtCore;
  }
  @Autowired
  public void setRequestCounter(RequestCounter requestCounter){
    this.requestCounter = requestCounter;
  }

  @GetMapping("/getAllUsers")
  public ResponseEntity<List<User>> getAllUsers() {
    requestCounter.incrementCounter();
    return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
  }

  @DeleteMapping("/deleteUser")
  public ResponseEntity<String> deleteUser(@RequestParam String username) {
    userService.deleteUser(username);
    return ResponseEntity.ok("User was successfully deleted");
  }

  @GetMapping("/getUsersByPlayer")
  public ResponseEntity<List<User>> getUsersByPlayer(@RequestParam String nickname) {
    return new ResponseEntity<>(userService.getUsersByPlayer(nickname), HttpStatus.OK);
  }

  @GetMapping("/getUserByName")
  public ResponseEntity<User> getUsersByName(@RequestParam String username) {
    return new ResponseEntity<>(userService.getUserByName(username), HttpStatus.OK);
  }

  @PostMapping("/addPlayer")
  public ResponseEntity<String> addPlayerToUser(
      @RequestHeader("Authorization") String authorizationHeader, @RequestParam String nickname) {
    String token = jwtCore.getTokenFromRequest(authorizationHeader);
    String username = jwtCore.getNameFromJwt(token);
    return ResponseEntity.ok(userService.addPlayerToUser(username, nickname));
  }

  @PostMapping("/addManyPlayers")
  public ResponseEntity<String> addManyPlayersToUser(
          @RequestHeader("Authorization") String authorizationHeader, @RequestParam List<String> nicknames) {
    String token = jwtCore.getTokenFromRequest(authorizationHeader);
    String username = jwtCore.getNameFromJwt(token);
    return ResponseEntity.ok(userService.addManyPlayersToUser(username, nicknames));
  }

  @GetMapping("/getPlayers")
  public ResponseEntity<Set<Player>> getAllPlayers(
      @RequestHeader("Authorization") String authorizationHeader) {
    String token = jwtCore.getTokenFromRequest(authorizationHeader);
    String username = jwtCore.getNameFromJwt(token);
    return ResponseEntity.ok(userService.getFavoritePlayersByUsername(username));
  }

  @DeleteMapping("/deletePlayer")
  public ResponseEntity<String> deletePlayer(
      @RequestHeader("Authorization") String authorizationHeader, @RequestParam String nickname) {
    String token = jwtCore.getTokenFromRequest(authorizationHeader);
    String username = jwtCore.getNameFromJwt(token);
    userService.removePlayer(username, nickname);
    return ResponseEntity.ok("Player was successfully deleted");
  }
}
