package org.gobeshona.qa.controller;

import org.gobeshona.qa.dto.PasswordChangeRequest;
import org.gobeshona.qa.dto.PasswordChangeResponse;
import org.gobeshona.qa.dto.SignupReq;
import org.gobeshona.qa.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DashboardController {

    @Autowired
    private PasswordService passwordService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/user/change-password")
    public String changePassword(Model model) {

        // Ensure the model has the necessary attributes
        if (!model.containsAttribute("passwordChangeRequest")) {
            model.addAttribute("passwordChangeRequest", new PasswordChangeRequest());
        }

        if (!model.containsAttribute("status")) {
            model.addAttribute("status", null);
        }

        if (!model.containsAttribute("message")) {
            model.addAttribute("message", null);
        }

        return "change_password";
    }

    @PostMapping("/user/change-password")
    public String changePassword(@ModelAttribute PasswordChangeRequest request,
                                 RedirectAttributes redirectAttributes) {
        PasswordChangeResponse response = passwordService.changePassword(request);

        // Pass status and message to the view using RedirectAttributes
        redirectAttributes.addFlashAttribute("status", response.isSuccess());
        redirectAttributes.addFlashAttribute("message", response.getMessage());

        // Redirect back to the change password page to display the message
        return "redirect:/user/change-password";
    }

}
