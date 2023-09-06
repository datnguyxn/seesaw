package com.seesaw.controller.api;

import com.seesaw.dto.request.OrderRequest;
import com.seesaw.dto.response.OrderResponse;
import com.seesaw.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class ApiOrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/add")
    public ResponseEntity<OrderResponse> addOrder(@RequestBody @Valid OrderRequest request){
        System.out.println(request);
        return ResponseEntity.ok().body(orderService.addOrder(request));
    }
    @GetMapping("/list")
    public ResponseEntity<Page<OrderResponse>> getAllOrder(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(orderService.getAllOrder(page, size));
    }
    @GetMapping("/get-orders-of-user")
    public ResponseEntity<List<OrderResponse>> getAllOrdersOfUser(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam String user_id){
        return ResponseEntity.ok().body(orderService.get(page, size, user_id));
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable(name = "id") String id,
            @RequestBody OrderRequest request){
        return ResponseEntity.ok().body(orderService.updateOrder(id,request));
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<OrderResponse> deleteOrder(@PathVariable(name = "id") String id){
        return ResponseEntity.ok().body(orderService.delete(id));
    }
}
