package com.seesaw.service;

import com.seesaw.dto.request.AddProductRequest;
import com.seesaw.model.CartDetailKey;
import com.seesaw.model.CartDetailModel;
import com.seesaw.model.CartModel;
import com.seesaw.model.ProductModel;
import com.seesaw.repository.CartDetailRepository;
import com.seesaw.repository.CartRepository;
import com.seesaw.repository.ProductRepository;
import com.seesaw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    public void add(AddProductRequest request) {
        CartModel cartModel = cartRepository.findByUserId(request.getUserId()).orElseThrow();
        ProductModel productModel = productRepository.findById(request.getProductId()).orElseThrow();
        CartDetailKey key = CartDetailKey.builder()
                .cartId(cartModel.getId())
                .productId(productModel.getId())
                .build();
        CartDetailModel cartDetailModel = CartDetailModel.builder()
                .id(key)
                .quantity(request.getQuantity())
                .price(productModel.getPrice()*request.getQuantity())
                .build();
        cartDetailRepository.save(cartDetailModel);
        cartModel.getCart_detail().add(cartDetailModel);
        cartRepository.save(cartModel);

    }

    public void save(List<CartModel> cartModels) {
        cartRepository.saveAll(cartModels);
    }
}
