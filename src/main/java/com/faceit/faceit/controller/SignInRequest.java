package com.faceit.faceit.controller;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
}

