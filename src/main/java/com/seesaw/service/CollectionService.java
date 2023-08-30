package com.seesaw.service;

import com.seesaw.dto.request.AddCategoryRequest;
import com.seesaw.dto.request.AddCollectionRequest;
import com.seesaw.dto.response.CategoryResponse;
import com.seesaw.dto.response.CollectionResponse;
import com.seesaw.dto.response.MailResponse;
import com.seesaw.dto.response.ProductResponse;
import com.seesaw.model.CategoryModel;
import com.seesaw.model.CollectionModel;
import com.seesaw.model.Mail;
import com.seesaw.model.ProductModel;
import com.seesaw.repository.CollectionRepository;
import com.seesaw.service.impl.MailServiceImpl;
import com.seesaw.utils.FileUploadUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    public CollectionResponse toResponse(CollectionModel collect){
        return CollectionResponse.builder()
                .id(collect.getId())
                .name(collect.getName())
                .description(collect.getDescription())
                .image(collect.getImage())
                .products(collect.getProducts() == null ? null : productService.toResponse(collect.getProducts()).stream().toList())
                .build();
    }
    public CollectionModel toEntity(AddCollectionRequest request) {
        return CollectionModel.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
    public List<CollectionResponse> toResponse(List<CollectionModel> collections) {
        return collections.stream().map(this::toResponse).toList();
    }
//    Create
    public CollectionResponse addCollection(AddCollectionRequest request){
        var collection = toEntity(request);
        var collectionSaved = collectionRepository.save(collection);
        try{
            FileUploadUtil.saveFile("collection",collection.getId() + ".jpg", request.getImage());
            collectionSaved.setImage("uploads/collections/" + collection.getId() + ".jpg");
            collectionRepository.save(collectionSaved);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        sendMailToIntroNewCollection();
        return toResponse(collection);
    }
//    Read
    public Page<?> get(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return collectionRepository.findAll(pageRequest).map(this::toResponse);
    }
    public List<CollectionResponse> get() {
        return toResponse(collectionRepository.findAll());
    }
    public List<CollectionResponse> findAll(){
        return toResponse(collectionRepository.findAll(Sort.by(Sort.Direction.ASC,"name")));
    }
    public CollectionResponse getCollectionById(String id) {
        CollectionModel collection = collectionRepository.findById(id).orElseThrow();
        return toResponse(collection);
    }
//    Update
    public CollectionResponse updateCollection(AddCollectionRequest request, String id){
        CollectionModel collection = collectionRepository.findById(id).orElseThrow();
        collection.setName(request.getName());
        collection.setDescription(request.getDescription());
        try{
            String file = collection.getImage();
            FileUploadUtil.deleteFile(file);
            FileUploadUtil.saveFile("collection",collection.getId() + ".jpg", request.getImage());
            collection.setImage("uploads/collections/" + collection.getId() + ".jpg");
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        collectionRepository.save(collection);
        return toResponse(collection);
    }
//    Delete
    @Transactional
    public List<CollectionResponse> deleteOneCollectionById(String id, int page, int size){
        CollectionModel collection = collectionRepository.findById(id).orElseThrow();
        productService.deleteProductOfCollection(id);
        collectionRepository.delete(collection);
        try{
            FileUploadUtil.deleteFile(collection.getImage());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return findAll();
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
