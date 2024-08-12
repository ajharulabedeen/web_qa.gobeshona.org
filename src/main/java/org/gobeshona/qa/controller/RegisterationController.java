package org.gobeshona.qa.controller;

import jakarta.validation.Valid;
import org.gobeshona.qa.dto.SignupReq;
import org.gobeshona.qa.dto.UserDto;
import org.gobeshona.qa.util.ErrorUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
@RequestMapping("public")
public class RegisterationController {
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

        return "redirect:/";
    }

}
