package com.seesaw.controller;

import com.seesaw.dto.request.ProductRequest;
import com.seesaw.model.ProductModel;
import com.seesaw.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/list")
    public String getAllProduct(Model model){
        model.addAttribute("products",productService.getAllProducts());
        return "pages/products/product";
    }
    @PostMapping("/add")
    public String addProduct(@RequestBody @Valid ProductRequest request){
        productService.addProduct(request);
        return "index";
    }
    @PutMapping("/update")
    public String updateProduct(){
        return null;
    }
}
