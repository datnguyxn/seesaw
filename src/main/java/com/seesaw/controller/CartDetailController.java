package com.seesaw.controller;

import com.seesaw.dto.request.AddProductRequest;
import com.seesaw.dto.response.CartDetailResponse;
import com.seesaw.model.CartDetailKey;
import com.seesaw.service.CartDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-detail")
public class CartDetailController {
    @Autowired
    private CartDetailService cartDetailService;
    @GetMapping("/list")
    public ResponseEntity<List<CartDetailResponse>> getAllProductOfCart(String cart_id){
        return ResponseEntity.ok().body(cartDetailService.getAllProductOfCart(cart_id));
    }
}
