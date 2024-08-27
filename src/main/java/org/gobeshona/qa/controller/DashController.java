package org.gobeshona.qa.controller;

import jakarta.servlet.http.HttpSession;
import org.gobeshona.qa.dto.LoginRequest;
import org.gobeshona.qa.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class DashController {

    @GetMapping("/dash")
    public String home() {
        return "dashboard";
    }


}
