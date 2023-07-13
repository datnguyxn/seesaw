package com.seesaw.controller;

import com.seesaw.dto.request.AddCategoryRequest;
import com.seesaw.dto.response.CategoryResponse;
import com.seesaw.model.CategoryModel;
import com.seesaw.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(categoryService.getAllCategories(page,size));
    }
    @GetMapping("/get-category")
    public ResponseEntity<CategoryResponse> getCategoryById(@RequestParam String id){
        return ResponseEntity.ok().body(categoryService.getCategoryById(id));
    }
}
