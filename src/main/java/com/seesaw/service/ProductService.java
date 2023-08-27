package com.seesaw.service;

import com.seesaw.dto.request.ProductRequest;
import com.seesaw.dto.response.ProductResponse;
import com.seesaw.model.CategoryModel;
import com.seesaw.model.CollectionModel;
import com.seesaw.model.ProductModel;
import com.seesaw.repository.CategoryRepository;
import com.seesaw.repository.CollectionRepository;
import com.seesaw.repository.FeedbackRepository;
import com.seesaw.repository.ProductRepository;
import com.seesaw.utils.FileUploadUtil;
import com.seesaw.utils.ProductSpecification;
import com.seesaw.utils.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private CartDetailService cartDetailService;
    @Autowired
    private InvoiceService invoiceService;
    public Set<ProductResponse> toResponse(Set<ProductModel> products) {
        return products.stream().map(this::toResponse).collect(Collectors.toSet());
    }
    public ProductResponse toResponse(ProductModel product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .image_path(product.getImage_path())
                .createdAt(product.getCreatedDate())
                .build();
    }
    public ProductModel toEntity(ProductRequest request){
        return ProductModel.builder()
                .id(request.getId())
                .name(request.getName())
                .brand(request.getBrand())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }
//    Create
    public ProductResponse addProduct(ProductRequest request){
        var product = productRepository.findById(request.getName()).orElse(null);
        var category = categoryRepository.findById(request.getCategory_id()).orElse(null);
        var collection = collectionRepository.findById(request.getCollection_id()).orElse(null);
        if(product == null && category != null && collection != null){
            var productEntity = toEntity(request);
            var productSaved = productRepository.save(productEntity);
            productSaved.setCategory(category);
            productSaved.setCollection(collection);
            String id = productSaved.getId();
            try{
                FileUploadUtil.saveFile("product", id + ".jpg", request.getImage_path());
                productSaved.setImage_path("/uploads/products/" + id + ".jpg");
                category.getProducts().add(productSaved);
                collection.getProducts().add(productSaved);
                categoryRepository.save(category);
                collectionRepository.save(collection);
                productRepository.save(productSaved);
            }catch(Exception e){
                throw new RuntimeException(e);
            }
            return toResponse(productSaved);
        }
        return updateProduct(request,product);
    }
//    Read
    public List<ProductResponse> getAllProducts(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        var products = productRepository.findAll(Sort.by(Sort.Direction.ASC, "name")).stream().map(this::toResponse);
        return products.toList();
    }
    public ProductResponse getProductById(String id){
        return toResponse(productRepository.findById(id).orElseThrow());
    }
    public List<ProductResponse> search(int page, int size, String value) {
        return productRepository.search(value).stream().map(this::toResponse).collect(Collectors.toList());
    }
    public List<ProductResponse> getAllProductOfCategory(int page, int size, String value) {
        return productRepository.findAllByCategory_Id(value).stream().map(this::toResponse).collect(Collectors.toList());
    }
    public List<ProductResponse> getAllProductOfCollection(int page, int size, String value) {
        return productRepository.findAllByCollection_Id(value).stream().map(this::toResponse).collect(Collectors.toList());
    }
    public List<ProductResponse> filter(int page, int size, String filter) {
        PageRequest pageRequest = PageRequest.of(page, size);
        var temp = filter.split(";");
        System.out.println("temp: "+ Arrays.toString(temp));
        List<SearchCriteria> fil = new ArrayList<>();
        if (temp.length > 0) {
            for (int i =0; i < temp.length; i++) {
                String key = temp[i].split(":")[0];
                String value = temp[i].split(":")[1];
                System.out.println("key: "+key);
                System.out.println("value: "+value);
                switch (key) {
                    case "category" ->{
                        fil.add(SearchCriteria.builder()
                                .key("category.id")
                                .operation("->")
                                .value(value)
                                .build());
                    }
                    case "brand" ->{
                        fil.add(SearchCriteria.builder()
                                .key("brand")
                                .operation(":")
                                .value(value)
                                .build());
                    }
                    case "priceFrom" ->{
                        fil.add(SearchCriteria.builder()
                                .key("price")
                                .operation(">=")
                                .value(value)
                                .build());
                    }
                    case "priceTo" ->{
                        fil.add(SearchCriteria.builder()
                                .key("price")
                                .operation("<=")
                                .value(value)
                                .build());
                    }
                    default -> {}
                }
            }
        }

        Specification<ProductModel> spec = Specification.where(null);
        for (var criteria : fil) {
            spec = spec.and(ProductSpecification.builder().criteria(criteria).build());
        }

        return productRepository.findAll(spec).stream().map(this::toResponse).collect(Collectors.toList());
    }
//    Update
    public ProductResponse updateProduct(ProductRequest request, String id){
        var product = productRepository.findById(id).orElseThrow();
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        try{
            String filePath = product.getImage_path();
            FileUploadUtil.deleteFile(filePath);
            FileUploadUtil.saveFile("product", id + ".jpg", request.getImage_path());
            product.setImage_path("/uploads/products/" + id + ".jpg");
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        product.setUpdatedDate(LocalDate.from(java.time.Instant.now()));
        productRepository.save(product);
        return toResponse(product);
    }
    public ProductResponse updateProduct(ProductRequest request, ProductModel product){
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity() + product.getQuantity());
        try{
            String filePath = product.getImage_path();
            FileUploadUtil.deleteFile(filePath);
            FileUploadUtil.saveFile("product", product.getId() + ".jpg", request.getImage_path());
            product.setImage_path("/uploads/products/" + product.getId() + ".jpg");
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        product.setUpdatedDate(LocalDate.from(java.time.Instant.now()));
        productRepository.save(product);
        return toResponse(product);
    }
//    Delete
    public List<ProductResponse> deleteProductById(String id, int page, int size){
        ProductModel product = productRepository.findById(id).orElseThrow();
        String filePath = product.getImage_path();
        try {
            FileUploadUtil.deleteFile(filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        feedbackService.deleteFeedbacksOfProduct(product);
        cartDetailService.deleteCartDetailOfProduct(product);
        invoiceService.deleteInvoicesOfProduct(product);
        productRepository.delete(product);
        return getAllProducts(page,size);
    }
    public void deleteProductOfCollection(String collection_id){
        List<ProductModel> product = productRepository.findAllByCollection_Id(collection_id);
        productRepository.deleteAll(product);
    }
    public void deleteProductOfCategory(String category_id){
        List<ProductModel> product = productRepository.findAllByCategory_Id(category_id);
        productRepository.deleteAll(product);
    }
    public void save(List<ProductModel> products) {
        productRepository.saveAll(products);
    }
}
