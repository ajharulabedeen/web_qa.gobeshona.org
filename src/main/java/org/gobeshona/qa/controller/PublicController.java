package org.gobeshona.qa.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.gobeshona.qa.dto.LoginRequest;
import org.gobeshona.qa.dto.LoginResponse;
import org.gobeshona.qa.dto.UserDto;
import org.gobeshona.qa.entity.User;
import org.gobeshona.qa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("public")
public class PublicController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("all")
    @ResponseBody
    public String all() {
        String apiUrl = "http://localhost:8080/api/test/all";
        return restTemplate.getForObject(apiUrl, String.class);
    }

    @GetMapping("token")
    @ResponseBody
    public String token(HttpSession session) {
        String url = "http://localhost:8080/api/auth/signin";
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
