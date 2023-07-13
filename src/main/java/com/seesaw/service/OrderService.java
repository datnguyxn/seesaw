package com.seesaw.service;

import com.seesaw.dto.request.OrderRequest;
import com.seesaw.dto.response.OrderResponse;
import com.seesaw.model.OrderModel;
import com.seesaw.model.UserModel;
import com.seesaw.repository.OrderRepository;
import com.seesaw.repository.UserRepository;
import org.apache.catalina.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    public OrderResponse convertOrder(OrderModel order){
        return OrderResponse.builder()
                .firstName(order.getFirstName())
                .lastName(order.getLastName())
                .email(order.getEmail())
                .phone(order.getPhone())
                .address(order.getAddress())
                .total_amount(order.getTotal_amount())
                .status(order.getStatus())
                .build();
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
            return convertOrder(order);
        }
        return null;
    }
    public List<OrderResponse> getAllOrder(){
        return orderRepository.findAll().stream().map(this::convertOrder).toList();
    }
    public List<OrderResponse> getAllOrderOfUser(String user_id){
        UserModel user = userRepository.findById(user_id).orElseThrow();
        return orderRepository.findByUsers(user).stream().map(this::convertOrder).toList();
    }
    public OrderResponse getOrderById(String id){
        return convertOrder(orderRepository.findById(id).orElseThrow());
    }
    public OrderResponse updateOrder(OrderRequest request){
        OrderModel order = orderRepository.findById(request.getId()).orElseThrow();
        order.setFirstName(request.getFirstName());
        order.setLastName(request.getLastName());
        order.setEmail(request.getEmail());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setStatus(request.getStatus());
        orderRepository.save(order);
        return convertOrder(order);
    }
    public void deleteOrderOfUser(UserModel user){
        List<OrderModel> order = orderRepository.findByUsers(user);
        orderRepository.deleteAll(order);
    }
    public void save(List<OrderModel> orderModels) {
        orderRepository.saveAll(orderModels);
    }
}
