package com.seesaw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping("")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "pages/auth/signin";
    }

    @GetMapping("/register")
    public String register() {
        return "pages/auth/signup";
    }

    @GetMapping("/logout")
    public String logout() {
        return "pages/auth/signin";
    }

    @GetMapping("/about")
    public String about() {
        return "pages/about";
    }
}
