package com.seesaw.controller;

import com.seesaw.auth.MessageResponse;
import com.seesaw.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment/")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/address")
    public ResponseEntity<MessageResponse> shippingInfo(@RequestBody InfoRequest request) {
        return ResponseEntity.ok(paymentService.checkInfo(request));
    }

}
