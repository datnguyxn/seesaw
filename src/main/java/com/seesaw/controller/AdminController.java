package com.seesaw.controller;

import com.seesaw.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request) {
        return adminService.redirectTo(request, "pages/admin/dashboard");
    }

    @GetMapping("/userAccount")
    public String getUserAccount(HttpServletRequest request) {
        return adminService.redirectTo(request, "pages/admin/userAccount");
    }

    @GetMapping("/product")
    public String getProduct(Model model, HttpServletRequest request) {
        return adminService.redirectTo(request, "pages/admin/product");
    }

    @GetMapping("/category")
    public String getCategory(Model model, HttpServletRequest request) {
        return adminService.redirectTo(request, "pages/admin/category");
    }

    @GetMapping("/collection")
    public String getCollection(Model model, HttpServletRequest request) {
        return adminService.redirectTo(request, "pages/admin/collection");
    }

    @GetMapping("/order")
    public String getOrder(Model model, HttpServletRequest request) {
        return adminService.redirectTo(request, "pages/admin/order");
    }

    @GetMapping("/login")
    public String login() {
        return "pages/admin/auth/signin";
    }
}
