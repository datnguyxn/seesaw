package com.seesaw.controller;

import com.seesaw.dto.request.OrderRequest;
import com.seesaw.dto.response.OrderResponse;
import com.seesaw.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/add")
    public ResponseEntity<OrderResponse> addOrder(@RequestBody @Valid OrderRequest request){
        return ResponseEntity.ok().body(orderService.addOrder(request));
    }
    @GetMapping("/list")
    public ResponseEntity<List<OrderResponse>> getAllOrder(){
        return null;
    }
}
