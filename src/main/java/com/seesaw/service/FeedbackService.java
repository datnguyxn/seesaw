package com.seesaw.service;

import com.seesaw.dto.request.AddFeedbackRequest;
import com.seesaw.model.FeedbackKey;
import com.seesaw.model.FeedbackModel;
import com.seesaw.model.ProductModel;
import com.seesaw.model.UserModel;
import com.seesaw.repository.FeedbackRepository;
import com.seesaw.repository.ProductRepository;
import com.seesaw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    public void addFeedback(AddFeedbackRequest request){
        UserModel user = userRepository.findById(request.getUserId()).orElseThrow();
        ProductModel product = productRepository.findById(request.getProductId()).orElseThrow();
        FeedbackKey key = FeedbackKey.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .build();
        FeedbackModel feedback = FeedbackModel.builder()
                .id(key)
                .note(request.getNote())
                .build();
        feedbackRepository.save(feedback);

    }
}
