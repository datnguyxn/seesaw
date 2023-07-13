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
    @GetMapping("/get-order")
    public ResponseEntity<OrderResponse> getOrder(@RequestParam String id){
        return ResponseEntity.ok().body(orderService.getOrderById(id));
    }
    @GetMapping("/list")
    public ResponseEntity<List<OrderResponse>> getAllOrder(){
        return ResponseEntity.ok().body(orderService.getAllOrder());
    }
    @GetMapping("/get-orders-of-user")
    public ResponseEntity<List<OrderResponse>> getAllOrdersOfUser(@RequestParam String user_id){
        return ResponseEntity.ok().body(orderService.getAllOrderOfUser(user_id));
    }
}
