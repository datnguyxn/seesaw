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
@RequestMapping("/categories")
public class ApiCategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody @Valid AddCategoryRequest category){
        return ResponseEntity.ok().body(categoryService.addCategory(category));
    }
    @PostMapping("/search-category")
    public ResponseEntity<CategoryResponse> getCategoryByName(@RequestBody @Valid String name){
        return ResponseEntity.ok().body(categoryService.getCategoryByName(name));
    }
    @PutMapping("/update")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestParam String id, @RequestBody AddCategoryRequest request){
        return ResponseEntity.ok().body(categoryService.updateCategory(request,id));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<List<CategoryResponse>> deleteOneCategoryById(
            @RequestParam String id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(categoryService.deleteCategoryById(id,page,size));
    }
}
