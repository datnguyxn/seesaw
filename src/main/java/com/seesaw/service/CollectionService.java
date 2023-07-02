package com.seesaw.service;

import com.seesaw.dto.request.CollectionRequest;
import com.seesaw.model.CollectionModel;
import com.seesaw.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionService {
    @Autowired
    private CollectionRepository collectionRepository;
//    Create
    public void addCollection(CollectionRequest request){
        CollectionModel collect = CollectionModel.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        collectionRepository.save(collect);
    }
//    Update
    public void updateCollection(CollectionRequest request,String id){
        CollectionModel collect = getOneCollectionById(id);
        collect.setName(request.getName());
        collect.setDescription(request.getDescription());
        collectionRepository.save(collect);
    }
//    Read
    public List<CollectionModel> getAllCollections(){
        List<CollectionModel> collections = new ArrayList<CollectionModel>();
        collectionRepository.findAll().forEach(collect -> collections.add(collect));
        return collections;
    }
    public CollectionModel getOneCollectionById(String id){
        return collectionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid collection id: "+id));
    }
    public CollectionModel getOneCollectionByName(String name){
        return collectionRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Invalid collection name: "+name));
    }
//    Delete
    public void deleteOneCollectionById(String id){
        CollectionModel collection = getOneCollectionById(id);
        if(collection != null){
            collectionRepository.delete(collection);
        }
    }
}
