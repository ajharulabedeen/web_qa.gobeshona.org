package org.gobeshona.qa.controller;

import org.gobeshona.qa.dto.SignupReq;
import org.gobeshona.qa.dto.UserDto;
import org.gobeshona.qa.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("index")
    public String home(){
        return "dashboard";
    }


//    @GetMapping("/login")
//    public String loginForm() {
//        return "login";
//    }

    /**
     * here wanted to keep the url 'login', then it will go to spring default page.
     * for that reason login_lte has been used.
     * @return
     */
    @GetMapping("/login_lte")
    public String loginFormLTE() {
        return "login_lte";
    }


    @PostMapping("/login")
    public String postLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        // Replace with actual authentication logic
//        if ("user@example.com".equals(username) && "password123".equals(password)) {
//            // Authentication successful
//            return "redirect:/home"; // Redirect to a secure page after successful login
//        } else {
//            // Authentication failed
//            model.addAttribute("error", true);
//            return "redirect:/login?error"; // Redirect back to the login page with an error message
//        }
        return "redirect:/index";
    }
    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }



}
