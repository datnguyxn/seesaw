package com.seesaw.service;

import com.seesaw.dto.request.AddCategoryRequest;
import com.seesaw.dto.response.CategoryResponse;
import com.seesaw.dto.response.CollectionResponse;
import com.seesaw.dto.response.ProductResponse;
import com.seesaw.model.CategoryModel;
import com.seesaw.model.CollectionModel;
import com.seesaw.model.ProductModel;
import com.seesaw.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;
    public CategoryResponse toResponse(CategoryModel category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .products(category.getProducts() == null ? null : productService.toResponse(category.getProducts()).stream().toList())
                .build();
    }
    public CategoryModel toEntity(AddCategoryRequest request) {
        return CategoryModel.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
    public List<CategoryResponse> toResponse(List<CategoryModel> categories) {
        return categories.stream().map(this::toResponse).toList();
    }
//    Create
    public CategoryResponse addCategory(AddCategoryRequest request){
        var category = toEntity(request);
        categoryRepository.save(category);
        return toResponse(category);
    }
//    Read
    public Page<?> get(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return categoryRepository.findAll(pageRequest).map(this::toResponse);
    }
    public List<CategoryResponse> get(){
        return toResponse(categoryRepository.findAll());
    }
    public List<CategoryResponse> findAll(){
        return toResponse(categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
    }
    public CategoryResponse getCategoryById(String id){
        CategoryModel category = categoryRepository.findById(id).orElseThrow();
        return toResponse(category);
    }
//    Update
    public CategoryResponse updateCategory(AddCategoryRequest request, String id){
        CategoryModel category = categoryRepository.findById(id).orElseThrow();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);
        return toResponse(category);
    }
//    Delete
    @Transactional
    public List<CategoryResponse> deleteCategoryById(String id){
        CategoryModel category = categoryRepository.findById(id).orElseThrow();
        productService.deleteProductOfCategory(id);
        categoryRepository.delete(category);
        return findAll();
    }
    public void save(List<CategoryModel> categoryModel) {
        categoryRepository.saveAll(categoryModel);
    }
}
