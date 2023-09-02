package com.seesaw.controller.api;

import com.seesaw.dto.request.AddCategoryRequest;
import com.seesaw.dto.response.CategoryResponse;
import com.seesaw.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class ApiCategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<CategoryResponse> addCategory(@ModelAttribute @Valid AddCategoryRequest category){
        return ResponseEntity.ok().body(categoryService.addCategory(category));
    }
    @GetMapping("/list")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(categoryService.get());
    }
    @GetMapping("/get-category")
    public ResponseEntity<CategoryResponse> getCategoryById(@RequestParam String id){
        return ResponseEntity.ok().body(categoryService.getCategoryById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryResponse> updateCategory(
            @RequestParam String id,
            @ModelAttribute AddCategoryRequest request
    ){
        return ResponseEntity.ok().body(categoryService.updateCategory(request,id));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<List<CategoryResponse>> deleteOneCategoryById(
            @RequestParam String id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(categoryService.deleteCategoryById(id));
    }
}
