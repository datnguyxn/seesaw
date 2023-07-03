package com.seesaw.controller;

import com.seesaw.dto.request.AddCollectionRequest;
import com.seesaw.dto.response.CollectionResponse;
import com.seesaw.model.CollectionModel;
import com.seesaw.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collections")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;
    @GetMapping("/showForm")
    public String showCollectionForm(CollectionModel collection, Model model) {
        model.addAttribute("collection", new CollectionModel());
        return "pages/collections/add-collection";
    }

    @PostMapping("/add")
    public CollectionModel addCollection(@RequestBody @Valid AddCollectionRequest collection){
        return collectionService.addCollection(collection);
    }
    @GetMapping("/list")
    public ResponseEntity<List<CollectionResponse>> getAllCollections(){
        return ResponseEntity.ok().body(collectionService.getAllCollections());
    }
    @PostMapping("/search-collection/{name}")
    public String getCollectionByName( @Valid String name){
        collectionService.getCollectionByName(name);
        return "pages/collections/collection";
    }
    @GetMapping("/get-collection/{id}")
    public CollectionModel getCollectionById(@PathVariable ("id") String id){
        return collectionService.getCollectionById(id);
    }
    @PutMapping("/update/{id}")
    public String updateCollection(@PathVariable ("id") String id, @Valid AddCollectionRequest request){
        collectionService.updateCollection(request,id);
        return "pages/collections/update-collection";
    }
    @GetMapping("/delete/{id}")
    public String deleteOneCollectionById(@PathVariable ("id") String id){
        collectionService.deleteOneCollectionById(id);
        return "pages/collections/collection";
    }
}
