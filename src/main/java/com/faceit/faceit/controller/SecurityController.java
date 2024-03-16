package com.faceit.faceit.controller;

import com.faceit.faceit.dao.CountryRepository;
import com.faceit.faceit.entity.*;
import com.faceit.faceit.dao.UserRepository;
import com.faceit.faceit.security.JwtCore;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/auth")
public class SecurityController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtCore jwtCore;

    private CountryRepository countryRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtCore(JwtCore jwtCore) {
        this.jwtCore = jwtCore;
    }

    @Autowired
    public void setCountyRepository(CountryRepository countyRepository){
        this.countryRepository=countyRepository;
    }

    @PostMapping("/signup")
    ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsUserByUsername(signUpRequest.getUsername()).booleanValue()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This nickname already exists");
        }
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));


        Country country = countryRepository.findCountryByCountryName(signUpRequest.getCountry());
        if (country == null) {
            country = new Country();
            country.setCountryName(signUpRequest.getCountry());
            countryRepository.save(country);
        }
        user.setCountry(country);
        user.setRole("ROLE_ADMIN");
        userRepository.save(user);
        Authentication authentication = null;
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtCore.generateToken(authentication);

        return ResponseEntity.ok(jwt);

    }

    @PostMapping("/signin")
    ResponseEntity<String> signin(@RequestBody SignInRequest signInRequest) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtCore.generateToken(authentication);
        return ResponseEntity.ok(jwt);
    }
    @PatchMapping("/editPassword")
    public ResponseEntity<String> editPassword(@RequestBody PasswordRequest passwordRequest, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        String username = jwtCore.getNameFromJwt(token);
        User user=userRepository.findUserByUsername(username).orElseThrow(()->new UsernameNotFoundException(
                String.format("User '%s' not found",username)));
        user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        userRepository.save(user);

        return  ResponseEntity.ok("Password was successfully updated");
    }
}
