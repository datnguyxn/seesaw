package com.seesaw.controller;

import com.seesaw.dto.request.AddCategoryRequest;
import com.seesaw.dto.response.CategoryResponse;
import com.seesaw.dto.response.CollectionResponse;
import com.seesaw.model.CategoryModel;
import com.seesaw.model.CollectionModel;
import com.seesaw.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody @Valid AddCategoryRequest category){
        return ResponseEntity.ok().body(categoryService.addCategory(category));
    }
    @GetMapping("/list")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }
    @PostMapping("/search-category/{name}")
    public ResponseEntity<CategoryModel> getCategoryByName(Model model, @RequestBody @Valid String name){
        return ResponseEntity.ok().body(categoryService.getCategoryByName(name));
    }
    @GetMapping("/get-category/{id}")
    public ResponseEntity<CategoryModel> getCategoryById(Model model,@PathVariable ("id") String id){
        return ResponseEntity.ok().body(categoryService.getCategoryById(id));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryModel> updateCategory(@PathVariable ("id") String id, @RequestBody AddCategoryRequest request){
        return ResponseEntity.ok().body(categoryService.updateCategory(request,id));
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<List<CategoryResponse>> deleteOneCategoryById(@PathVariable ("id") String id){
        return ResponseEntity.ok().body(categoryService.deleteCategoryById(id));
    }
}
