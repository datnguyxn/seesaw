package com.seesaw.controller.api;

import com.seesaw.dto.request.AddCollectionRequest;
import com.seesaw.dto.response.CollectionResponse;
import com.seesaw.dto.response.MailResponse;
import com.seesaw.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collections")
public class ApiCollectionController {
    @Autowired
    private CollectionService collectionService;

    @PostMapping("/add")
    public ResponseEntity<CollectionResponse> addCollection(@RequestBody @Valid AddCollectionRequest collection) {
        return ResponseEntity.ok().body(collectionService.addCollection(collection));
    }

    @PutMapping("/update")
    public ResponseEntity<CollectionResponse> updateCollection(@RequestParam String id, @RequestBody @Valid AddCollectionRequest request) {
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
