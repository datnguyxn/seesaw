package com.seesaw.controller;

import com.seesaw.dto.request.AddInfoRequest;
import com.seesaw.dto.request.AddPaymentRequest;
import com.seesaw.dto.response.MessageResponse;
import com.seesaw.service.OrderService;
import com.seesaw.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class PaymentController {

    @GetMapping("")
    public String checkout() {
        return "pages/user/checkout";
    }
}
