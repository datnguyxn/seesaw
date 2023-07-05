package com.seesaw.service;

import com.seesaw.dto.request.AddCollectionRequest;
import com.seesaw.dto.response.CollectionResponse;
import com.seesaw.dto.response.ProductResponse;
import com.seesaw.model.CollectionModel;
import com.seesaw.model.ProductModel;
import com.seesaw.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionService {
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private ProductService productService;
//    Create
    public CollectionResponse addCollection(AddCollectionRequest request){
        CollectionModel collect = CollectionModel.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        collectionRepository.save(collect);
        return CollectionResponse.builder()
                .name(collect.getName())
                .description(collect.getDescription())
                .products(new ArrayList<ProductResponse>())
                .build();
    }
//    Read
    public List<CollectionResponse> getAllCollections() {
        List<CollectionResponse> collections = collectionRepository.findAll().stream().map(collection->{
            List<ProductResponse> productResponses = new ArrayList<ProductResponse>();
            for(ProductModel p : collection.getProducts()){
                ProductResponse productResponse = new ProductResponse();
                productResponse.setId(p.getId());
                productResponse.setName(p.getName());
                productResponse.setDescription(p.getDescription());
                productResponse.setBrand(p.getBrand());
                productResponse.setPrice(p.getPrice());
                productResponse.setQuantity(p.getQuantity());
                productResponses.add(productResponse);
            }
            CollectionResponse collectionResponse = CollectionResponse.builder()
                    .name(collection.getName())
                    .description(collection.getDescription())
                    .products(productResponses)
                    .build();
            return collectionResponse;
        }).toList();
        return collections;
    }
    public CollectionModel getCollectionById(String id){
        return collectionRepository.findById(id).orElse(null);
    }
    public CollectionModel getCollectionByName(String name){
        return collectionRepository.findByName(name).orElse(null);
    }
//    Update
    public CollectionModel updateCollection(AddCollectionRequest request, String id){
        CollectionModel collect = getCollectionById(id);
        collect.setName(request.getName());
        collect.setDescription(request.getDescription());
        collectionRepository.save(collect);
        return collect;
    }
//    Delete
    public List<CollectionResponse> deleteOneCollectionById(String id){
        CollectionModel collection = getCollectionById(id);
        if(collection != null){
            productService.deleteProductOfCollection(collection);
            collectionRepository.delete(collection);
        }
        return getAllCollections();
    }
}
