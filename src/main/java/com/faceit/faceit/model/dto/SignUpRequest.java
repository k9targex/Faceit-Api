package com.faceit.faceit.model.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private String country;
}
