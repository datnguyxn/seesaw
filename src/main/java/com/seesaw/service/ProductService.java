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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public ProductResponse convertProduct(ProductModel product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }
//    Create
    public ProductResponse addProduct(ProductRequest request){
        ProductModel pro = productRepository.findById(request.getName()).orElse(null);
        CategoryModel cate = categoryRepository.findById(request.getCategory_id()).orElse(null);
        CollectionModel collect = collectionRepository.findById(request.getCollection_id()).orElse(null);
        if(pro == null){
            if(cate != null && collect != null){
                ProductModel product = ProductModel.builder()
                        .id(request.getId())
                        .name(request.getName())
                        .brand(request.getBrand())
                        .description(request.getDescription())
                        .price(request.getPrice())
                        .quantity(request.getQuantity())
                        .date_created(Date.from(java.time.Instant.now()))
                        .date_updated(Date.from(java.time.Instant.now()))
                        .collection(collect)
                        .category(cate)
                        .build();
                productRepository.save(product);
                collect.getProducts().add(product);
                cate.getProducts().add(product);
                collectionRepository.save(collect);
                categoryRepository.save(cate);
                return convertProduct(product);
            }
        }
        return updateProduct(request,pro);
    }
//    Read
    public List<ProductResponse> getAllProducts(int page, int size, boolean sorted, String type, String by){
        PageRequest pageRequest = PageRequest.of(page, size);
        if (sorted && by.equals("asc")) {
            pageRequest.withSort(Sort.by(type).ascending());
        } else if (sorted && by.equals("desc")){
            pageRequest.withSort(Sort.by(type).descending());
        }
        var products = productRepository.findAll(pageRequest).stream().map(this::convertProduct);

        return products.toList();
    }
    public List<ProductResponse> getAllProducts(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        var products = productRepository.findAll(pageRequest).stream().map(this::convertProduct);

        return products.toList();
    }
    public ProductResponse getProductById(String id){
        return convertProduct(productRepository.findById(id).orElseThrow());
    }
    public List<ProductResponse> searchProductByName(String name){
        return productRepository.findByName(name).stream().map(this::convertProduct).toList();
    }
    public List<ProductResponse> searchProductByBrand(String brand){
        return productRepository.findByBrand(brand).stream().map(this::convertProduct).toList();
    }
//    Update
    public ProductResponse updateProduct(ProductRequest request, String id){
        ProductModel product = productRepository.findById(id).orElseThrow();
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImage_path(request.getImage_path());
        product.setDate_updated(Date.from(java.time.Instant.now()));
        productRepository.save(product);
        return convertProduct(product);
    }
    public ProductResponse updateProduct(ProductRequest request, ProductModel product){
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity() + product.getQuantity());
        product.setImage_path(request.getImage_path());
        product.setDate_updated(Date.from(java.time.Instant.now()));
        productRepository.save(product);
        return convertProduct(product);
    }
//    Delete
    public List<ProductResponse> deleteProductById(String id, int page, int size){
        ProductModel product = productRepository.findById(id).orElseThrow();
        feedbackService.deleteFeedbacksOfProduct(product);
        cartDetailService.deleteCartDetailOfProduct(product);
        invoiceService.deleteInvoicesOfProduct(product);
        productRepository.delete(product);
        return getAllProducts(page,size);
    }
    public void deleteProductOfCollection(CollectionModel collect){
        List<ProductModel> product = productRepository.findByCollection(collect);
        productRepository.deleteAll(product);
    }
    public void deleteProductOfCategory(CategoryModel cate){
        List<ProductModel> product = productRepository.findByCategory(cate);
        productRepository.deleteAll(product);
    }
    public void save(List<ProductModel> products) {
        productRepository.saveAll(products);
    }
}
