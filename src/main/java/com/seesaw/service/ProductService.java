package com.seesaw.service;

import com.seesaw.dto.request.ProductRequest;
import com.seesaw.model.CategoryModel;
import com.seesaw.model.CollectionModel;
import com.seesaw.model.ProductModel;
import com.seesaw.repository.CategoryRepository;
import com.seesaw.repository.CollectionRepository;
import com.seesaw.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
//    Create
    public void addProduct(ProductRequest request){
        ProductModel po = productRepository.findByName(request.getName()).orElse(null);
        CategoryModel cate = categoryRepository.findById(request.getCategory_id()).orElse(null);
        CollectionModel collect = collectionRepository.findById(request.getCollection_id()).orElse(null);
        if(po == null){
            if(cate != null && collect != null){
                ProductModel product = ProductModel.builder()
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
            }
        }else{
            updateProduct(request,po);
        }
    }
//    Read
    public List<ProductModel> getAllProducts(){
        List<ProductModel> products = new ArrayList<ProductModel>();
        productRepository.findAll().forEach(collect -> products.add(collect));
        return products;
    }
    public ProductModel getProductById(String id){
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product id: "+id));
    }
    public ProductModel searchProductByName(String name){
        return productRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Invalid product name: "+name));
    }
    public ProductModel searchProductByBrand(String brand){
        return productRepository.findByName(brand).orElseThrow(() -> new IllegalArgumentException("Invalid product brand: "+brand));
    }
//    Update
    public void updateProduct(ProductRequest request, String id){
        ProductModel product = getProductById(id);
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImage_path(request.getImage_path());
        product.setDate_updated(Date.from(java.time.Instant.now()));
        productRepository.save(product);
    }
    public void updateProduct(ProductRequest request, ProductModel product){
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity() + product.getQuantity());
        product.setImage_path(request.getImage_path());
        product.setDate_updated(Date.from(java.time.Instant.now()));
        productRepository.save(product);
    }
//    Delete
    public void deleteProductById(String id){
        ProductModel product = getProductById(id);
        if(product != null){
            productRepository.delete(product);
        }
    }
    public void deleteProductOfCollection(CollectionModel collect){
        List<ProductModel> product = productRepository.findByCollection(collect);
        for(ProductModel p: product){
            productRepository.delete(p);
        }
    }
}
