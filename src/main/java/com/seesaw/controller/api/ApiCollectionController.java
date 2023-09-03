package com.seesaw.controller.api;

import com.seesaw.dto.request.AddCollectionRequest;
import com.seesaw.dto.response.CollectionResponse;
import com.seesaw.dto.response.MailResponse;
import com.seesaw.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
public class ApiCollectionController {
    @Autowired
    private CollectionService collectionService;

    @PostMapping("/add")
    public ResponseEntity<CollectionResponse> addCollection(@ModelAttribute @Valid AddCollectionRequest collection) {
        return ResponseEntity.ok().body(collectionService.addCollection(collection));
    }
    @GetMapping("/list")
    public ResponseEntity<Page<CollectionResponse>> getAllCollections(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(collectionService.get(page, size));
    }
    @GetMapping("/get-collection")
    public ResponseEntity<CollectionResponse> getCollectionById(@RequestParam String id){
        return ResponseEntity.ok().body(collectionService.getCollectionById(id));
    }
    @PutMapping("/update")
    public ResponseEntity<CollectionResponse> updateCollection(
            @RequestParam String id,
            @ModelAttribute @Valid AddCollectionRequest request
    ) {
        return ResponseEntity.ok().body(collectionService.updateCollection(request, id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<CollectionResponse>> deleteOneCollectionById(
            @RequestParam String id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(collectionService.deleteOneCollectionById(id, page, size));
    }

    @PostMapping("/send-mail")
    public ResponseEntity<MailResponse> sendMail() {
        return ResponseEntity.ok(collectionService.sendMailToIntroNewCollection());
    }
}
