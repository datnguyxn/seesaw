package com.seesaw.controller.api;

import com.seesaw.dto.request.AddProductRequest;
import com.seesaw.dto.response.CartDetailResponse;
import com.seesaw.model.CartDetailKey;
import com.seesaw.service.CartDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart-detail")
public class ApiCartDetailController {
    @Autowired
    private CartDetailService cartDetailService;
    @PostMapping("/add")
    public void add(@RequestBody @Valid AddProductRequest request){
        cartDetailService.addToCart(request);
    }
    @PostMapping("/delete")
    public ResponseEntity<List<CartDetailResponse>> deleteProductOfCart(@RequestBody @Valid CartDetailKey id){
        return ResponseEntity.ok().body(cartDetailService.deleteProductOfCart(id));
    }
}
