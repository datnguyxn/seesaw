package com.seesaw.controller;

import com.seesaw.dto.response.CollectionResponse;
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
    @GetMapping("/list")
    public ResponseEntity<List<CollectionResponse>> getAllCollections(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(collectionService.getAllCollections(page,size));
    }
    @GetMapping("/search-collection")
    public ResponseEntity<CollectionResponse> getCollectionByName(@RequestParam @Valid String name){
        return ResponseEntity.ok().body(collectionService.getCollectionByName(name));
    }
    @GetMapping("/get-collection")
    public ResponseEntity<CollectionResponse> getCollectionById(@RequestParam String id){
        return ResponseEntity.ok().body(collectionService.getCollectionById(id));
    }
}
