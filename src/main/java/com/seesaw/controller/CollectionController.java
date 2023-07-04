package com.seesaw.controller;

import com.seesaw.dto.request.AddCollectionRequest;
import com.seesaw.dto.response.CollectionResponse;
import com.seesaw.model.CollectionModel;
import com.seesaw.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collections")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;
    @PostMapping("/add")
    public ResponseEntity<CollectionResponse> addCollection(@RequestBody @Valid AddCollectionRequest collection){
        return ResponseEntity.ok().body(collectionService.addCollection(collection));
    }
    @GetMapping("/list")
    public ResponseEntity<List<CollectionResponse>> getAllCollections(){
        return ResponseEntity.ok().body(collectionService.getAllCollections());
    }
    @PostMapping("/search-collection/{name}")
    public ResponseEntity<CollectionModel> getCollectionByName(@RequestBody @Valid String name){
        return ResponseEntity.ok().body(collectionService.getCollectionByName(name));
    }
    @GetMapping("/get-collection/{id}")
    public ResponseEntity<CollectionModel> getCollectionById(@PathVariable ("id") String id){
        return ResponseEntity.ok().body(collectionService.getCollectionById(id));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<CollectionModel> updateCollection(@PathVariable ("id") String id, @Valid AddCollectionRequest request){
        return ResponseEntity.ok().body(collectionService.updateCollection(request,id));
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<List<CollectionResponse>> deleteOneCollectionById(@PathVariable ("id") String id){
        return ResponseEntity.ok().body(collectionService.deleteOneCollectionById(id));
    }
}
