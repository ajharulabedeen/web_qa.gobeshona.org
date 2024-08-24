package org.gobeshona.qa.controller;

import jakarta.validation.Valid;
import org.gobeshona.qa.dto.SignupReq;
import org.gobeshona.qa.util.ErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;


import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("public")
public class RegisterationController {

    @Value("${api.qa.base-url}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("signupReq", new SignupReq());
        return "signup";
    }

    @PostMapping("/signup")
    public String submitSignupForm(@Valid SignupReq signupReq, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Log the error if necessary
            List<String> errorMessages = ErrorUtils.getDefaultMessages(result.getAllErrors());
            // Add attributes to the model to pass to the view
            model.addAttribute("status", 500);  // or any other status code
            model.addAttribute("error", "Internal Server Error");
            model.addAttribute("message", errorMessages);
            return "error";  // this is the name of the error page (error.html)
        }

        try {
            // Send the signup request to the external API
            String signupApiUrl = baseUrl + "/api/auth/signup";

            if (signupReq.getUsernameType().toLowerCase().equals("email")){
                signupReq.setUsername(signupReq.getEmail());
            }else {
                signupReq.setUsername(signupReq.getMobile());
            }

            signupReq.setRole(new HashSet<>());


            restTemplate.postForEntity(signupApiUrl, signupReq, String.class);

            // If successful, redirect to the home page or another success page
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            // Handle errors from the API call
            model.addAttribute("status", 500);
            model.addAttribute("error", "Internal Server Error");
//            model.addAttribute("message", "There was an error processing your signup request.");
            model.addAttribute("message", e.getMessage());
            return "error";
        }

    }

}
