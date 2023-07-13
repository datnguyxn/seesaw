package com.seesaw.service;

import com.seesaw.dto.request.AddCategoryRequest;
import com.seesaw.dto.response.CategoryResponse;
import com.seesaw.dto.response.CollectionResponse;
import com.seesaw.dto.response.ProductResponse;
import com.seesaw.model.CategoryModel;
import com.seesaw.model.CollectionModel;
import com.seesaw.model.ProductModel;
import com.seesaw.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CategoryResponse convertCategory(CategoryModel category, List<ProductResponse> products){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .products(products)
                .build();
    }
    public CategoryResponse convertCategory(CategoryModel category){
        List<ProductResponse> productResponses = category.getProducts().stream().map(product -> {
            return productService.convertProduct(product);
        }).toList();
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .products(productResponses)
                .build();
    }
//    Create
    public CategoryResponse addCategory(AddCategoryRequest request){
        CategoryModel category = CategoryModel.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
        categoryRepository.save(category);
        return convertCategory(category,new ArrayList<ProductResponse>());
    }
//    Read
    public List<CategoryResponse> getAllCategories(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size).withSort(Sort.by("name").ascending());
        List<CategoryResponse> categories = categoryRepository.findAll(pageRequest).stream().map(category->{
            List<ProductResponse> productResponses = new ArrayList<ProductResponse>();
            for(ProductModel p : category.getProducts()){
                ProductResponse productResponse = productService.convertProduct(p);
                productResponses.add(productResponse);
            }
            return convertCategory(category,productResponses);
        }).toList();
        return categories;
    }
    public CategoryResponse getCategoryById(String id){
        CategoryModel category = categoryRepository.findById(id).orElseThrow();
        return convertCategory(category);
    }
    public CategoryResponse getCategoryByName(String name){
        CategoryModel category = categoryRepository.findByName(name).orElseThrow();
        return convertCategory(category);
    }
//    Update
    public CategoryResponse updateCategory(AddCategoryRequest request, String id){
        CategoryModel category = categoryRepository.findById(id).orElseThrow();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);
        return convertCategory(category);
    }
//    Delete
    public List<CategoryResponse> deleteCategoryById(String id, int page, int size){
        CategoryModel category = categoryRepository.findById(id).orElseThrow();
        productService.deleteProductOfCategory(category);
        categoryRepository.delete(category);
        return getAllCategories(page,size);
    }
    public void save(List<CategoryModel> categoryModel) {
        categoryRepository.saveAll(categoryModel);
    }
}
