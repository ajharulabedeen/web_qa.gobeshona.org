package org.gobeshona.qa.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Value("${api.qa.base-url}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest request; // Inject HttpServletRequest


    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Prepare request to external API
        try {
            Map<String, String> request = Map.of("username", username, "password", password);
            Map<String, Object> response = restTemplate.postForObject(baseUrl+"/api/auth/web-signin", request, Map.class);

            if (response != null && response.containsKey("accessToken")) {
                // Verify received credentials
                String receivedUsername = response.get("username").toString();
                String receivedPassword = response.get("password").toString();

//                Get the session from the request and set the accessToken
                HttpServletRequest currentRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
                HttpSession session = currentRequest.getSession();

                session.setAttribute("accessToken", response.get("accessToken").toString());


                if (username.equals(receivedUsername) && passwordEncoder.matches(password, receivedPassword)) {
                    UserDetails userDetails = User.withUsername(receivedUsername)
                            .password(receivedPassword)
                            .roles("USER")
                            .build();

                    return new UsernamePasswordAuthenticationToken(userDetails, receivedPassword, userDetails.getAuthorities());
                } else {
                    throw new BadCredentialsException("Invalid credentials");
                }
            } else {
                throw new BadCredentialsException("Invalid credentials");
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors (e.g., 4xx errors)
            throw new BadCredentialsException("Invalid credentials");
        } catch (ResourceAccessException e) {
            // Handle connection errors
            throw new AuthenticationServiceException("Server error. Please try again later.", e);
        } catch (Exception e) {
            // Handle other exceptions
            throw new AuthenticationServiceException("An unexpected error occurred. Please try again later.", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}