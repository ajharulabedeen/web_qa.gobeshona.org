package org.gobeshona.qa.controller;

import jakarta.servlet.http.HttpSession;
import org.gobeshona.qa.dto.LoginRequest;
import org.gobeshona.qa.dto.LoginResponse;

import org.gobeshona.qa.dto.PasswordResetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

@Controller
@RequestMapping("public")
public class PublicController {

    @Value("${api.qa.base-url}")
    private String baseUrl; // Inject the base URL from properties


    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    public String home() {
        return "welcome";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot_password";
    }


    // Handle the POST request to reset the password
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("username") String username, Model model) {

        String url = baseUrl + "/reset-password"; // Endpoint URL for the API call

        // Prepare request parameters
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);

        try {
            // Make the API call to reset the password
            PasswordResetResponse response = restTemplate.postForObject(url, params, PasswordResetResponse.class);

            // Check the response status and add messages to the model
            if (response != null && response.getStatus()) {
                model.addAttribute("message", "New password has been sent to your mobile/email.");
            } else {
                model.addAttribute("error", response != null ? response.getMessage() : "Failed to reset password.");
            }
        } catch (RestClientException e) {
            model.addAttribute("error", "An error occurred while resetting the password: " + e.getMessage());
        }


        // Redirect back to the login page with appropriate messages
        return "login"; // Redirect to the login page or any other page as per your flow
    }


    @GetMapping("all")
    @ResponseBody
    public String all() {
        String apiUrl = "http://localhost:8080/api/test/all";
        return restTemplate.getForObject(apiUrl, String.class);
    }

    @GetMapping("token")
    @ResponseBody
    public String token(HttpSession session) {
        String url = "http://localhost:8082/api/auth/signin";
        LoginRequest request = new LoginRequest();
        request.setUsername("khan1");
        request.setPassword("123456");

        LoginResponse response = restTemplate.postForObject(url, request, LoginResponse.class);

        // Store the token in session
        if (response != null && response.getAccessToken() != null) {
            session.setAttribute("accessToken", response.getAccessToken());
        }

        return response.getAccessToken();
    }

}
