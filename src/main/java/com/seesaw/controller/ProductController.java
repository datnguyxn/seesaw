package com.seesaw.controller;

import com.seesaw.dto.request.ProductRequest;
import com.seesaw.dto.response.ProductResponse;
import com.seesaw.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> getAllProduct(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sorted", defaultValue = "false") boolean sorted,
            @RequestParam(name = "type", defaultValue = "name") String type,
            @RequestParam(name = "by", defaultValue = "asc") String by
    ){
        return ResponseEntity.ok().body(productService.getAllProducts(page, size, sorted, type, by));
    }
    @GetMapping("/get-product")
    public ResponseEntity<ProductResponse> getProduct(@RequestParam String id){
        return ResponseEntity.ok().body(productService.getProductById(id));
    }
}
