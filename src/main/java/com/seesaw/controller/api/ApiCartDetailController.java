package com.seesaw.controller.api;

import com.seesaw.dto.request.AddProductRequest;
import com.seesaw.dto.response.CartDetailResponse;
import com.seesaw.service.CartDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-detail")
public class ApiCartDetailController {
    @Autowired
    private CartDetailService cartDetailService;
    @PostMapping("/add")
    public void add(@RequestBody @Valid AddProductRequest request){
        cartDetailService.addToCart(request);
    }
    @GetMapping("/list")
    public ResponseEntity<CartDetailResponse> getAllProductOfCart(@RequestParam String cart_id){
        return ResponseEntity.ok().body(cartDetailService.getAllProductOfCart(cart_id));
    }
    @PostMapping("/delete")
    public ResponseEntity<CartDetailResponse> deleteProductOfCart(
            @RequestParam String product_id,
            @RequestParam String cart_id
    ){
        return ResponseEntity.ok().body(cartDetailService.deleteProductOfCart(product_id, cart_id));
    }
}
