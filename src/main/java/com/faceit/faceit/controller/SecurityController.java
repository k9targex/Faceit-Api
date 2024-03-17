package com.faceit.faceit.controller;


import com.faceit.faceit.model.dto.PasswordRequest;
import com.faceit.faceit.model.dto.SignInRequest;
import com.faceit.faceit.model.dto.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.faceit.faceit.service.SecurityService;



@Controller
@RequestMapping("/auth")
public class SecurityController {
    private SecurityService securityService;
    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/signup")
    ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest) {

        try {
            return ResponseEntity.ok(securityService.register(signUpRequest));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }


    @PostMapping("/signin")
    ResponseEntity<String> signin(@RequestBody SignInRequest signInRequest) {
        try{
            return ResponseEntity.ok(securityService.login(signInRequest));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @PatchMapping("/editPassword")
    public ResponseEntity<String> editPassword(@RequestBody PasswordRequest passwordRequest, HttpServletRequest request) {
        securityService.changePas(passwordRequest,request);
        return  ResponseEntity.ok("Password was successfully updated");
    }
}
