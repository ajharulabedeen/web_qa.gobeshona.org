package org.gobeshona.qa.service;

import jakarta.servlet.http.HttpSession;
import org.gobeshona.qa.dto.PasswordChangeRequest;
import org.gobeshona.qa.dto.PasswordChangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class PasswordService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpSession session;

    private static final String CHANGE_PASSWORD_URL = "http://localhost:8082/api/users/change-password"; // Replace with your API URL

    public PasswordChangeResponse changePassword(PasswordChangeRequest request) {
        String token = (String) session.getAttribute("accessToken");

        if (token == null) {
            return new PasswordChangeResponse(false, "Token not found in session");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PasswordChangeRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<PasswordChangeResponse> response = restTemplate.exchange(
                    CHANGE_PASSWORD_URL,
                    HttpMethod.PUT,
                    entity,
                    PasswordChangeResponse.class
            );

            return response.getBody();
        } catch (RestClientException e) {
            return new PasswordChangeResponse(false, "Error calling change password API: " + e.getMessage());
        }
    }
}
