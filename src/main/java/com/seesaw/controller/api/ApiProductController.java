package com.seesaw.controller.api;

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
public class ApiProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/add")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody @Valid ProductRequest request){
        return ResponseEntity.ok().body(productService.addProduct(request));
    }
    @PostMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestBody String value,@RequestParam String type){
        if(type.equals("name")){
            return ResponseEntity.ok().body(productService.searchProductByName(value));
        }else if(type.equals("brand")){
            return ResponseEntity.ok().body(productService.searchProductByBrand(value));
        }
        return null;
    }
    @PutMapping("/update")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody @Valid ProductRequest request, @RequestParam String id){
        return ResponseEntity.ok().body(productService.updateProduct(request,id));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<List<ProductResponse>> deleteProductById(
            @RequestParam String id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(productService.deleteProductById(id,page,size));
    }
}
