package com.seesaw.controller;

import com.seesaw.dto.request.ProductRequest;
import com.seesaw.dto.response.CategoryResponse;
import com.seesaw.dto.response.ProductResponse;
import com.seesaw.model.ProductModel;
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
    @PostMapping("/add")
    public ProductResponse addProduct(@RequestBody @Valid ProductRequest request){
        return productService.addProduct(request);
    }
    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> getAllProduct(){
        return ResponseEntity.ok().body(productService.getAllProducts());
    }
    @GetMapping("/sort-asc")
    public ResponseEntity<List<ProductResponse>> sortProductAsc(){
        return ResponseEntity.ok().body(productService.sortProductAsc("name"));
    }
    @GetMapping("/sort-desc")
    public ResponseEntity<List<ProductResponse>> sortProductDesc(){
        return ResponseEntity.ok().body(productService.sortProductDesc("name"));
    }
    @GetMapping("/sort-price-asc")
    public ResponseEntity<List<ProductResponse>> sortProductPriceAsc(){
        return ResponseEntity.ok().body(productService.sortProductAsc("price"));
    }
    @GetMapping("/sort-price-desc")
    public ResponseEntity<List<ProductResponse>> sortProductPriceDesc(){
        return ResponseEntity.ok().body(productService.sortProductAsc("price"));
    }
    @PutMapping("/update")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody @Valid ProductRequest request, @PathVariable ("id") String id){
        return ResponseEntity.ok().body(productService.updateProduct(request,id));
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<List<ProductResponse>> deleteProductById(@PathVariable ("id") String id){
        return ResponseEntity.ok().body(productService.deleteProductById(id));
    }
}
