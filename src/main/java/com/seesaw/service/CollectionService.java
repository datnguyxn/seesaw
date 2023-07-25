package com.seesaw.service;

import com.seesaw.dto.request.AddCollectionRequest;
import com.seesaw.dto.response.CollectionResponse;
import com.seesaw.dto.response.MailResponse;
import com.seesaw.dto.response.ProductResponse;
import com.seesaw.model.CollectionModel;
import com.seesaw.model.Mail;
import com.seesaw.model.ProductModel;
import com.seesaw.repository.CollectionRepository;
import com.seesaw.service.impl.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionService {
    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private UserService userService;

    public CollectionResponse convertCollection(CollectionModel collect, List<ProductResponse> products){
        return CollectionResponse.builder()
                .id(collect.getId())
                .name(collect.getName())
                .description(collect.getDescription())
                .products(products)
                .build();
    }
    public CollectionResponse convertCollection(CollectionModel collect){
        List<ProductResponse> productResponses = collect.getProducts().stream().map(product -> {
            return productService.convertProduct(product);
        }).toList();
        return CollectionResponse.builder()
                .id(collect.getId())
                .name(collect.getName())
                .description(collect.getDescription())
                .products(productResponses)
                .build();
    }
//    Create
    public CollectionResponse addCollection(AddCollectionRequest request){
        CollectionModel collect = CollectionModel.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
        collectionRepository.save(collect);
        return convertCollection(collect,new ArrayList<ProductResponse>());
    }
//    Read
    public List<CollectionResponse> getAllCollections(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size).withSort(Sort.by("name").ascending());
        List<CollectionResponse> collections = collectionRepository.findAll(pageRequest).stream().map(collection->{
            List<ProductResponse> productResponses = new ArrayList<ProductResponse>();
            for(ProductModel p : collection.getProducts()){
                ProductResponse productResponse = productService.convertProduct(p);
                productResponses.add(productResponse);
            }
            return convertCollection(collection,productResponses);
        }).toList();
        return collections;
    }
    public CollectionResponse getCollectionById(String id) {
        CollectionModel collect = collectionRepository.findById(id).orElseThrow();
        return convertCollection(collect);
    }
    public CollectionResponse getCollectionByName(String name){
        CollectionModel collect = collectionRepository.findByName(name).orElseThrow();
        return convertCollection(collect);
    }
//    Update
    public CollectionResponse updateCollection(AddCollectionRequest request, String id){
        CollectionModel collect = collectionRepository.findById(id).orElseThrow();
        collect.setName(request.getName());
        collect.setDescription(request.getDescription());
        collectionRepository.save(collect);
        return convertCollection(collect);
    }
//    Delete
    public List<CollectionResponse> deleteOneCollectionById(String id, int page, int size){
        CollectionModel collection = collectionRepository.findById(id).orElseThrow();
        productService.deleteProductOfCollection(collection);
        collectionRepository.delete(collection);
        return getAllCollections(page,size);
    }
    public void save(List<CollectionModel> collectionModel) {
        collectionRepository.saveAll(collectionModel);
    }

    public MailResponse sendMailToIntroNewCollection() {
        var users = userService.getAllUsers();
        for (var user: users) {
            Mail mail = Mail.builder()
                    .to(user.getEmail())
                    .subject("New Collection")
                    .content("images")
                    .build();
        mailService.sendEmail(mail, "mail-newProduct.html");
        }
        return MailResponse.builder()
                .message("Send mail successfully")
                .status(true)
                .build();
    }
}
