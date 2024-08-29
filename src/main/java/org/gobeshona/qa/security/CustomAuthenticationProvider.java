package org.gobeshona.qa.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final RestTemplate restTemplate;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(RestTemplate restTemplate, BCryptPasswordEncoder passwordEncoder) {
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            // Make a request to the external API
            AuthenticationRequest request = new AuthenticationRequest(username, password);
            AuthenticationResponse response = restTemplate.postForObject(
                    "http://localhost:8082/api/auth/web-signin", request, AuthenticationResponse.class);

            if (response.getUsername().equals(username) && passwordEncoder.matches(response.getPassword(), password) ){
                UserDetails userDetails = User.builder()
                        .username(response.getUsername())
                        .password(response.getPassword())
                        .authorities(response.getRoles().toArray(new String[0]))
                        .build();
                return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
            }else {
                return null;
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Authentication failed");
        }
        finally {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
