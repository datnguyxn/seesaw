package com.seesaw.controller.api;

import com.seesaw.dto.request.AddProductRequest;
import com.seesaw.dto.request.CartProduct;
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
        System.out.println(request);
        cartDetailService.addToCart(request);
    }
    @GetMapping("/list")
    public ResponseEntity<CartDetailResponse> getAllProductOfCart(@RequestParam String cart_id){
        return ResponseEntity.ok().body(cartDetailService.getAllProductOfCart(cart_id));
    }
    @PutMapping("/update")
    public void updateProductOfCart(
            @RequestParam String cart_id,
            @RequestBody @Valid List<CartProduct> products
    ){
        cartDetailService.updateCart(cart_id, products);
    }
    @PostMapping("/update-quantity")
    public void updateQuantityOfProduct(
            @RequestParam String cart_id,
            @RequestParam String product_id,
            @RequestParam int quantity
    ){
        cartDetailService.updateQuantity(cart_id, product_id, quantity);
    }
    @PostMapping("/delete")
    public ResponseEntity<CartDetailResponse> deleteProductOfCart(
            @RequestParam String product_id,
            @RequestParam String cart_id
    ){
        return ResponseEntity.ok().body(cartDetailService.deleteProductOfCart(product_id, cart_id));
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<?> deleteAllProductOfCart(@RequestParam String cart_id){
        System.out.println(cart_id);
        cartDetailService.deleteCartDetailOfCart(cart_id);
        return ResponseEntity.ok().build();
    }
}
