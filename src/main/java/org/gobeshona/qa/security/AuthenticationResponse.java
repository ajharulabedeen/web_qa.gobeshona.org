package org.gobeshona.qa.security;

import lombok.Data;

import java.util.List;

@Data
public class AuthenticationResponse {
    private Long id;
    private String username;
    private String password;
    private String email;
    private List<String> roles;
    private String accessToken;
    private String tokenType;
}
