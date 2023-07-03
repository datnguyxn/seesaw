package com.seesaw.service;

import com.seesaw.dto.request.AddCategoryRequest;
import com.seesaw.model.CategoryModel;
import com.seesaw.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
//    Create
    public void addCategory(AddCategoryRequest request){
        CategoryModel category = CategoryModel.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        categoryRepository.save(category);
    }
//    Update
    public void updateCategory(AddCategoryRequest request, String id){
        CategoryModel category = getCategoryById(id);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);
    }
//    Read
    public List<CategoryModel> getAllCategories(){
        List<CategoryModel> categories = new ArrayList<CategoryModel>();
        categoryRepository.findAll().forEach(category -> categories.add(category));
        return categories;
    }
    public CategoryModel getCategoryById(String id){
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid collection id: "+id));
    }
    public CategoryModel getCategoryByName(String name){
        return categoryRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Invalid collection name: "+name));
    }
//    Delete
    public void deleteOneCategoryById(String id){
        CategoryModel category = getCategoryById(id);
        if(category != null){
            categoryRepository.delete(category);
        }
    }
}
