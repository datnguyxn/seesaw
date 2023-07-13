package com.seesaw.controller.api;

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
public class ApiOrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/add")
    public ResponseEntity<OrderResponse> addOrder(@RequestBody @Valid OrderRequest request){
        return ResponseEntity.ok().body(orderService.addOrder(request));
    }
    @PutMapping("/update")
    public ResponseEntity<OrderResponse> updateOrder(@RequestBody @Valid OrderRequest request){
        return ResponseEntity.ok().body(orderService.updateOrder(request));
    }
}
