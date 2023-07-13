package com.seesaw.controller;

import com.seesaw.dto.request.AddFeedbackRequest;
import com.seesaw.dto.response.FeedbackResponse;
import com.seesaw.model.FeedbackKey;
import com.seesaw.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;
    @GetMapping("/list")
    public ResponseEntity<List<FeedbackResponse>> getAllFeedbacks(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(feedbackService.getAllFeedbacks(page,size));
    }
    @GetMapping("/get-feedback-of-product")
    public ResponseEntity<List<FeedbackResponse>> getFeedbackOfProduct(@RequestParam String id){
        return ResponseEntity.ok().body(feedbackService.getFeedbacksByProductId(id));
    }
}
