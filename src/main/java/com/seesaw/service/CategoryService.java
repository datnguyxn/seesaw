package com.seesaw.service;

import com.seesaw.dto.request.AddCategoryRequest;
import com.seesaw.dto.response.CategoryResponse;
import com.seesaw.dto.response.ProductResponse;
import com.seesaw.model.CategoryModel;
import com.seesaw.model.ProductModel;
import com.seesaw.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;
//    Create
    public CategoryResponse addCategory(AddCategoryRequest request){
        CategoryModel category = CategoryModel.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        categoryRepository.save(category);
        return CategoryResponse.builder()
                .name(category.getName())
                .description(category.getDescription())
                .products(new ArrayList<ProductResponse>())
                .build();
    }
//    Read
    public List<CategoryResponse> getAllCategories() {
        List<CategoryResponse> categories = categoryRepository.findAll().stream().map(category->{
            List<ProductResponse> productResponses = new ArrayList<ProductResponse>();
            for(ProductModel p : category.getProducts()){
                ProductResponse productResponse = new ProductResponse();
                productResponse.setId(p.getId());
                productResponse.setName(p.getName());
                productResponse.setDescription(p.getDescription());
                productResponse.setBrand(p.getBrand());
                productResponse.setPrice(p.getPrice());
                productResponse.setQuantity(p.getQuantity());
                productResponses.add(productResponse);
            }
            CategoryResponse categoryResponse = CategoryResponse.builder()
                    .name(category.getName())
                    .description(category.getDescription())
                    .products(productResponses)
                    .build();
            return categoryResponse;
        }).toList();
        return categories;
    }
    public CategoryModel getCategoryById(String id){
        return categoryRepository.findById(id).orElse(null);
    }
    public CategoryModel getCategoryByName(String name){
        return categoryRepository.findByName(name).orElse(null);
    }
//    Update
    public CategoryModel updateCategory(AddCategoryRequest request, String id){
        CategoryModel category = getCategoryById(id);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);
        return category;
    }
//    Delete
    public List<CategoryResponse> deleteCategoryById(String id){
        CategoryModel category = getCategoryById(id);
        if(category != null){
            productService.deleteProductOfCategory(category);
            categoryRepository.delete(category);
        }
        return getAllCategories();
    }
}
