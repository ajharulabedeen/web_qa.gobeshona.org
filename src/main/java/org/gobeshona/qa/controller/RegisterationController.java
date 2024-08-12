package org.gobeshona.qa.controller;

import jakarta.validation.Valid;
import org.gobeshona.qa.dto.SignupReq;
import org.gobeshona.qa.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            return "signup";
        }

        // Backend validation for the mobile number format if usernameType is mobile
        if ("mobile".equals(signupReq.getUsernameType())) {
            String mobile = signupReq.getMobile();
            if (!mobile.matches("^0\\d{10}$")) {
                result.rejectValue("mobile", "error.signupReq", "Mobile number must be 11 digits and start with 0.");
                return "signup";
            }
        }

        // Further processing (e.g., save to database)
        return "redirect:/";
    }

}
