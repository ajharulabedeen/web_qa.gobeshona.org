package org.gobeshona.qa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("public")
public class PublicController {

    @GetMapping("registration")
    public String registration() {
        return "public/registration-form"; // Assuming you have a Thymeleaf template named registration-form.html in the public directory
    }
}
