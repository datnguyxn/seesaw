package com.seesaw.service;
import com.seesaw.dto.request.ProductRequest;
import com.seesaw.dto.response.ProductResponse;
import com.seesaw.model.CategoryModel;
import com.seesaw.model.CollectionModel;
import com.seesaw.model.ProductModel;
import com.seesaw.repository.CategoryRepository;
import com.seesaw.repository.CollectionRepository;
import com.seesaw.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ProductResponse convertProduct(ProductModel product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setBrand(product.getBrand());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());
        return productResponse;
    }
//    Create
    public ProductResponse addProduct(ProductRequest request){
        ProductModel pro = productRepository.findByName(request.getName()).orElse(null);
        CategoryModel cate = categoryRepository.findById(request.getCategory_id()).orElse(null);
        CollectionModel collect = collectionRepository.findById(request.getCollection_id()).orElse(null);
        if(pro == null){
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
                ProductResponse p = convertProduct(product);
                return p;
            }
        }
        return updateProduct(request,pro);
    }
//    Read
    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll().stream().map(product->{
            ProductResponse p = convertProduct(product);
            return p;
        }).toList();
    }
    public ProductModel getProductById(String id){
        return productRepository.findById(id).orElse(null);
    }
    public ProductModel searchProductByName(String name){
        return productRepository.findByName(name).orElse(null);
    }
    public ProductModel searchProductByBrand(String brand){
        return productRepository.findByName(brand).orElse(null);
    }
    public List<ProductResponse> sortProductAsc(String column){
        return productRepository.findAll(Sort.by(Sort.Direction.ASC,column)).stream().map(product->{
            ProductResponse p = convertProduct(product);
            return p;
        }).toList();
    }
    public List<ProductResponse> sortProductDesc(String column){
        return productRepository.findAll(Sort.by(Sort.Direction.DESC,column)).stream().map(product->{
            ProductResponse p = convertProduct(product);
            return p;
        }).toList();
    }
//    Update
    public ProductResponse updateProduct(ProductRequest request, String id){
        ProductModel product = getProductById(id);
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImage_path(request.getImage_path());
        product.setDate_updated(Date.from(java.time.Instant.now()));
        productRepository.save(product);
        ProductResponse p = convertProduct(product);
        return p;
    }
    public ProductResponse updateProduct(ProductRequest request, ProductModel product){
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity() + product.getQuantity());
        product.setImage_path(request.getImage_path());
        product.setDate_updated(Date.from(java.time.Instant.now()));
        productRepository.save(product);
        ProductResponse p = convertProduct(product);
        return p;
    }
//    Delete
    public List<ProductResponse> deleteProductById(String id){
        ProductModel product = getProductById(id);
        if(product != null){
            productRepository.delete(product);
        }
        return getAllProducts();
    }
    public void deleteProductOfCollection(CollectionModel collect){
        List<ProductModel> product = productRepository.findByCollection(collect);
        for(ProductModel p: product){
            productRepository.delete(p);
        }
    }
    public void deleteProductOfCategory(CategoryModel cate){
        List<ProductModel> product = productRepository.findByCategory(cate);
        for(ProductModel p: product){
            productRepository.delete(p);
        }
    }

    public void save(List<ProductModel> products) {
        productRepository.saveAll(products);
    }

    public ProductModel findByCollectionId(String id) {
        return productRepository.findById(id).orElse(null);
    }
}
