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
@RequestMapping("/api/products")
public class ApiProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/add")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody @Valid ProductRequest request){
        return ResponseEntity.ok().body(productService.addProduct(request));
    }
    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> getAllProduct(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(productService.getAllProducts(page, size));
    }
    @GetMapping("/info")
    public ResponseEntity<ProductResponse> getProduct(@RequestParam String id){
        return ResponseEntity.ok().body(productService.getProductById(id));
    }
    @GetMapping("/get-category/{category_id}")
    public ResponseEntity<?> getProductCategory(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @PathVariable(name = "category_id") String category_id
    ){
        return ResponseEntity.ok().body(productService.getAllProductOfCategory(page, size, category_id));
    }
    @GetMapping("/get-collection/{collection_id}")
    public ResponseEntity<?> getProductCollection(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @PathVariable(name = "collection_id") String collection_id
    ){
        return ResponseEntity.ok().body(productService.getAllProductOfCollection(page, size, collection_id));
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "value") String value
    ){
        return ResponseEntity.ok().body(productService.search(page, size, value));
    }
    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponse>> filterProduct(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "filter") String filter
    ){
        return ResponseEntity.ok().body(productService.filter(page, size, filter));
    }
    @PutMapping("/update")
    public ResponseEntity<ProductResponse> updateProduct(
            @RequestBody @Valid ProductRequest request,
            @RequestParam String id){
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
