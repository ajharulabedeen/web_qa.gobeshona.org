package org.gobeshona.qa.controller;

import jakarta.servlet.http.HttpSession;
import org.gobeshona.qa.dto.LoginRequest;
import org.gobeshona.qa.dto.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("public")
public class PublicController {

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
