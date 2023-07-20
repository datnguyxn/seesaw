package com.seesaw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/search")
    public String search() {
        return "pages/user/search";
    }

    @GetMapping("/cart")
    public String cart() {
        return "pages/user/cart";
    }

    @GetMapping("/productDetail")
    public String productDetail() {
        return "pages/user/productDetail";
    }

    @GetMapping("/about")
    public String about() {
        return "pages/user/about";
    }

    @GetMapping("/contact")
    public String contact() { return "pages/user/contact"; }

    @GetMapping("/products")
    public String products() {return "pages/user/products";}

    @GetMapping("/order")
    public String order() {return "pages/user/order";}

//    @GetMapping("/order")
//    public String order() {return "mail/mail-newProduct";}


    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "pages/auth/forgotpassword";
    }

}
