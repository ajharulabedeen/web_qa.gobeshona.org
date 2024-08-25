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


    @GetMapping("/login")
    public String loginForm() {
        return "login";
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
    @GetMapping("register/new")
    public String showRegistrationForm_(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register_";
    }
    @GetMapping("public/newregister")
    public String showPublicRegistration(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register_public";
    }

    @GetMapping("register/singup_me")
    public String singUp(Model model){
        SignupReq user = new SignupReq();
        model.addAttribute("signupRequest", user);
        return "signup";
    }



    // handler method to handle register user form submit request
//    @PostMapping("/register/save")
//    public String registration(@Valid @ModelAttribute("user") UserDto user,
//                               BindingResult result,
//                               Model model){
//        User existing = userService.findByEmail(user.getEmail());
//        if (existing != null) {
//            result.rejectValue("email", null, "There is already an account registered with that email");
//        }
//        if (result.hasErrors()) {
//            model.addAttribute("user", user);
//            return "register";
//        }
//        userService.saveUser(user);
//        return "redirect:/register?success";
//    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}
