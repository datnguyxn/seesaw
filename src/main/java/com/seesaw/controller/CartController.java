package com.seesaw.controller;

import com.seesaw.dto.request.AddProductRequest;
import com.seesaw.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public void add(@RequestBody AddProductRequest request) {
        cartService.add(request);

    }
}
