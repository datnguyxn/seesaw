package com.seesaw.controller;

import com.seesaw.service.ProductService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;


@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminController {
    @Autowired
    private ProductService productService;

    @GetMapping("/dashboard")
    public String getDashboard(HttpServletRequest request) {
        if (checkURL(request)) {
            return "pages/admin/dashboard";
        } else {
            return "pages/404/404";
        }
    }

    @GetMapping("/userAccount")
    public String getUserAccount(HttpServletRequest request) {
        if (checkURL(request)) {
            return "pages/admin/userAccount";
        } else {
            return "pages/404/404";
        }
    }

    @GetMapping("/product")
    public String getProduct(Model model, HttpServletRequest request) {
//        model.addAttribute("products", productService.getAllProducts());
        if (checkURL(request)) {
            return "pages/admin/product";
        } else {
            return "pages/404/404";
        }
    }

    @GetMapping("/category")
    public String getCategory(Model model, HttpServletRequest request) {
        if (checkURL(request)) {
            return "pages/admin/category";
        } else {
            return "pages/404/404";
        }
    }

    @GetMapping("/collection")
    public String getCollection(Model model, HttpServletRequest request) {
        if (checkURL(request)) {
            return "pages/admin/collection";
        } else {
            return "pages/404/404";
        }
    }

    @GetMapping("/orders")
    public String getOrder(Model model, HttpServletRequest request) {
        if (checkURL(request)) {
            return "pages/admin/dashboard";
        } else {
            return "pages/404/404";
        }
    }

    private boolean checkURL(HttpServletRequest request) {
        Cookie cookies = WebUtils.getCookie(request, "role");

        if (cookies != null) {
            return cookies.getValue().equals("ADMIN");
        }
        return false;
    }
}
