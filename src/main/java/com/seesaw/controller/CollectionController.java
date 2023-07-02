package com.seesaw.controller;

import com.seesaw.dto.request.CollectionRequest;
import com.seesaw.model.CollectionModel;
import com.seesaw.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/collections")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;
    @GetMapping("/showForm")
    public String showCollectionForm(CollectionModel collection, Model model) {
        model.addAttribute("collection", new CollectionModel());
        return "add-collection";
    }

    @PostMapping("/add")
    public String addCollection(@RequestBody @Valid CollectionRequest request){
        collectionService.addCollection(request);
        return "index";
    }

    @PutMapping("/update/{id}")
    public String updateCollection(@PathVariable ("id") String id, @RequestBody CollectionRequest request){
        collectionService.updateCollection(request,id);
        return "index";
    }

    @GetMapping("/list")
    public String getAllCollections(Model model){
        model.addAttribute("collections",collectionService.getAllCollections());
        return "collection";
    }

    @PostMapping("/search-collection/{name}")
    public String getCollectionByName(Model model,@RequestBody @Valid String name){
        model.addAttribute("one-collection",collectionService.getOneCollectionByName(name));
        return "collection";
    }

    @GetMapping("/get-collection/{id}")
    public String getCollectionById(Model model,@PathVariable ("id") String id){
        model.addAttribute("one-collection",collectionService.getOneCollectionById(id));
        return "collection";
    }

    @GetMapping("/delete/{id}")
    public String deleteOneCollectionById(@PathVariable ("id") String id){
        collectionService.deleteOneCollectionById(id);
        return "index";
    }
}
