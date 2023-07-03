package com.seesaw.controller;

import com.seesaw.dto.request.AddCategoryRequest;
import com.seesaw.model.CategoryModel;
import com.seesaw.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/showForm")
    public String showCategoryForm(CategoryModel category, Model model) {
        model.addAttribute("category", new CategoryModel());
        return "add-category";
    }

    @PostMapping("/add")
    public String addCategory(@RequestBody @Valid AddCategoryRequest category){
        categoryService.addCategory(category);
        return "index";
    }

    @PutMapping("/update/{id}")
    public String updateCategory(@PathVariable ("id") String id, @RequestBody AddCategoryRequest request){
        categoryService.updateCategory(request,id);
        return "category";
    }

    @GetMapping("/list")
    public String getAllCategories(Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        return "category";
    }
    @PostMapping("/search-category/{name}")
    public String getCategoryByName(Model model,@RequestBody @Valid String name){
        model.addAttribute("one-category",categoryService.getCategoryByName(name));
        return "category";
    }

    @GetMapping("/get-category/{id}")
    public String getCategoryById(Model model,@PathVariable ("id") String id){
        model.addAttribute("one-category",categoryService.getCategoryById(id));
        return "category";
    }

    @GetMapping("/delete/{id}")
    public String deleteOneCategoryById(@PathVariable ("id") String id){
        categoryService.deleteOneCategoryById(id);
        return "category";
    }
}
