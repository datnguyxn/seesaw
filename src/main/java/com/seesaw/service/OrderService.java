package com.seesaw.service;

import com.seesaw.dto.request.OrderRequest;
import com.seesaw.dto.response.OrderResponse;
import com.seesaw.model.OrderModel;
import com.seesaw.model.UserModel;
import com.seesaw.repository.OrderRepository;
import com.seesaw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    public OrderResponse convertOrder(OrderModel order){
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setFirstName(order.getFirstName());
        orderResponse.setLastName(order.getLastName());
        orderResponse.setEmail(order.getEmail());
        orderResponse.setPhone(order.getPhone());
        orderResponse.setAddress(order.getAddress());
        orderResponse.setTotal_amount(order.getTotal_amount());
        orderResponse.setStatus(order.getStatus());
        return orderResponse;
    }
    public OrderResponse addOrder(OrderRequest request){
        UserModel user = userRepository.findById(request.getUser_id()).orElse(null);
        if(user != null){
            OrderModel order = OrderModel.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .total_amount(request.getTotal_amount())
                    .date_created(Date.from(java.time.Instant.now()))
                    .status("unpaid")
                    .users(user)
                    .build();
            orderRepository.save(order);
            user.getOrders().add(order);
            userRepository.save(user);
            OrderResponse o = convertOrder(order);
            return o;
        }
        return null;
    }
}
