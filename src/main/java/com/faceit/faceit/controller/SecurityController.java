package com.faceit.faceit.controller;

import com.faceit.faceit.model.dto.PasswordRequest;
import com.faceit.faceit.model.dto.SignInRequest;
import com.faceit.faceit.model.dto.SignUpRequest;
import com.faceit.faceit.service.SecurityService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class SecurityController {
  private SecurityService securityService;

  @Autowired
  public void setSecurityService(SecurityService securityService) {
    this.securityService = securityService;
  }

  @PostMapping("/signup")
  ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest) {
    return ResponseEntity.ok(securityService.register(signUpRequest));
  }
  
@CrossOrigin(origins = "/", allowCredentials = "true")
@ResponseStatus(HttpStatus.OK)
@PostMapping("/signin")
public void signin(@RequestBody SignInRequest signInRequest, HttpServletResponse response) {
  String token = securityService.login(signInRequest);
  Cookie cookie = new Cookie("token", token);
  cookie.setHttpOnly(true);
  cookie.setSecure(true);
  cookie.setPath("/");
  response.addCookie(cookie);
}

@GetMapping("/secure")
public ResponseEntity<String> authenticate() {
  return ResponseEntity.ok("df");
}

  @PatchMapping("/editPassword")
  public ResponseEntity<String> editPassword(
      @RequestBody PasswordRequest passwordRequest, HttpServletRequest request) {
    securityService.changePas(passwordRequest, request);
    return ResponseEntity.ok("Password was successfully updated");
  }
}
