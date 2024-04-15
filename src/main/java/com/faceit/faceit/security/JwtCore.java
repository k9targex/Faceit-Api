package com.faceit.faceit.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Component
public class JwtCore {
  @Value("${faceit.app.secret}")
  private String secret;

  @Value("${faceit.app.expirationMs}")
  private int lifetime;

  public String generateToken(Authentication authentication) {
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return Jwts.builder()
        .setSubject((userDetails.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + lifetime))
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

  public String getNameFromJwt(String token) {
    return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody().getSubject();
  }
  public String getTokenFromRequest(String authorizationHeader) {
    String token;
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      token = authorizationHeader.substring(7);
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    return token;
  }
}
