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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<MessageResponse> payment(@RequestBody AddPaymentRequest request, HttpServletRequest req, HttpServletResponse res) {
        return ResponseEntity.ok(paymentService.payment(request, req, res));
    }


}
