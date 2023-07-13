package com.seesaw.service;

import com.seesaw.dto.request.AddFeedbackRequest;
import com.seesaw.dto.response.FeedbackResponse;
import com.seesaw.model.FeedbackKey;
import com.seesaw.model.FeedbackModel;
import com.seesaw.model.ProductModel;
import com.seesaw.model.UserModel;
import com.seesaw.repository.FeedbackRepository;
import com.seesaw.repository.ProductRepository;
import com.seesaw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    public FeedbackResponse convertFeedback(FeedbackModel feedback){
        return FeedbackResponse.builder()
                .id(feedback.getId())
                .user(feedback.getUsers().getFirstname() + " " + feedback.getUsers().getLastname())
                .product(feedback.getProducts().getName())
                .note(feedback.getNote())
                .rating(feedback.getRating())
                .build();
    }
    public FeedbackResponse addFeedback(AddFeedbackRequest request){
        UserModel user = userRepository.findById(request.getUser_id()).orElseThrow();
        ProductModel product = productRepository.findById(request.getProduct_id()).orElseThrow();
        FeedbackKey key = FeedbackKey.builder()
                .userId(user.getId())
                .productId(product.getId())
                .build();
        FeedbackModel feedback = FeedbackModel.builder()
                .id(key)
                .note(request.getNote())
                .rating(request.getRating())
                .products(product)
                .users(user)
                .build();
        feedbackRepository.save(feedback);
        user.getFeedbacks().add(feedback);
        product.getFeedbacks().add(feedback);
        userRepository.save(user);
        productRepository.save(product);
        return convertFeedback(feedback);
    }
    public List<FeedbackResponse> getAllFeedbacks(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size).withSort(Sort.by("rating").descending());
        return feedbackRepository.findAll(pageRequest).stream().map(this::convertFeedback).toList();
    }
    public List<FeedbackResponse> getFeedbacksByProductId(String productId){
        return feedbackRepository.findByProductId(productId).stream().map(this::convertFeedback).toList();
    }
    public List<FeedbackResponse> deleteFeedbackById(FeedbackKey id, int page, int size){
        feedbackRepository.deleteByUserIdAndProductId(id.getUserId(),id.getProductId());
        return getAllFeedbacks(page,size);
    }
    public void deleteFeedbacksOfProduct(ProductModel product){
        List<FeedbackModel> feedback = feedbackRepository.findByProducts(product);
        feedback.forEach(f -> feedbackRepository.deleteByUserIdAndProductId(f.getId().getUserId(),f.getId().getProductId()));
    }
    public void save(List<FeedbackModel> feedbackModels) {
        feedbackRepository.saveAll(feedbackModels);
    }
}
