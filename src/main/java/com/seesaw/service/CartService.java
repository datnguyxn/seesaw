package com.seesaw.service;

import com.seesaw.model.*;
import com.seesaw.repository.CartRepository;
import com.seesaw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailService cartDetailService;

    public void addCart(CartModel request) {
        UserModel user = userRepository.findById(request.getUser().getId()).orElseThrow();
        CartModel cart = CartModel.builder()
                .id(request.getId())
                .total_amount(request.getTotal_amount())
                .user(user)
                .build();
        cartRepository.save(cart);
    }

    public void deleteCartOfUser(UserModel user) {
        var cart = cartRepository.findByUser(user);
        if (cart.isPresent()) {
            if (cart.get().getCart_detail().isEmpty()) {
                cartRepository.delete(cart.get());
            } else {
                cartDetailService.deleteCartDetailOfCart(cart.get());
                cartRepository.delete(cart.get());
            }
        }
    }
}
