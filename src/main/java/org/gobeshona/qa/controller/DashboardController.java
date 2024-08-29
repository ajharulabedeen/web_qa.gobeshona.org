package org.gobeshona.qa.controller;

import org.gobeshona.qa.dto.SignupReq;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dash")
    public String dashboard(Model model) {
        return "dashboard";
    }

}
