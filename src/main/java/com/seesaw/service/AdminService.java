package com.seesaw.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

@Service
public class AdminService {

    public boolean isAdminRequire(HttpServletRequest request) {
        Cookie cookie =  WebUtils.getCookie(request, "role");
        if(cookie != null) {
            if(cookie.getValue().equals("ADMIN")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public String redirectTo(HttpServletRequest request, String url) {
        System.out.println("url: " + url);
        if (isAdminRequire(request)) {
            return url;
        } else {
            return "redirect:/error/403";
        }
    }
}
