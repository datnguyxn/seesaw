package com.seesaw.service;

import com.seesaw.auth.MessageResponse;
import com.seesaw.dto.request.AddInfoRequest;
import com.seesaw.exception.UserNotFoundException;
import com.seesaw.model.OrderModel;
import com.seesaw.repository.OrderRepository;
import com.seesaw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
//
//@Service
//@RequiredArgsConstructor
public class PaymentService {
//
//    private final UserRepository userRepository;
//    private final OrderRepository orderRepository;
//
//    public MessageResponse checkInfo(AddInfoRequest request) {
//        var user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new UserNotFoundException("User not found"));
//        OrderModel orderModel = OrderModel.builder()
//                .firstName(user.getFirstname())
//                .lastName(user.getLastname())
//                .email(user.getEmail())
//                .phone(user.getContact())
//                .address(request.getAddress())
//                .zipCode(request.getZipCode())
//                .optional(request.getOptional())
//                .date_created(Date.from(new Date().toInstant()))
//                .date_updated(null)
//                .users(user)
//                .build();
//        var newOrder = orderRepository.save(orderModel);
//        return MessageResponse.builder()
//                .message("Success")
//                .build();
//    }
//
//    public MessageResponse updateInfoOrder(AddInfoRequest request) {
//        var user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new UserNotFoundException("User not found"));
//        return MessageResponse.builder()
//                .message("Success")
//                .build();
//    }
//
}
