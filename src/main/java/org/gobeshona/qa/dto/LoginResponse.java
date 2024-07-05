package org.gobeshona.qa.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private String accessToken;
    private String tokenType;

    // Getters and setters
}