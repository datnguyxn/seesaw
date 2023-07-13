package com.seesaw.controller.api;

import com.seesaw.model.CartModel;
import com.seesaw.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class ApiCartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public void add(@RequestBody CartModel request) {
        cartService.addCart(request);
    }
}
