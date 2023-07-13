package com.seesaw.service;

import com.seesaw.dto.request.AddProductRequest;
import com.seesaw.dto.response.CartDetailResponse;
import com.seesaw.model.*;
import com.seesaw.repository.CartRepository;
import com.seesaw.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seesaw.repository.CartDetailRepository;

import java.util.List;

@Service
public class CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    public CartDetailResponse convertCartDetail(CartDetailModel cartDetail){
        return CartDetailResponse.builder()
                .id(cartDetail.getId())
                .product(cartDetail.getProducts().getName())
                .price(cartDetail.getPrice())
                .quantity(cartDetail.getQuantity())
                .build();
    }
    public void addToCart(AddProductRequest request) {
        CartModel cartModel = cartRepository.findById(request.getCart_id()).orElseThrow();
        ProductModel productModel = productRepository.findById(request.getProduct_id()).orElseThrow();
        CartDetailKey key = CartDetailKey.builder()
                .cartId(cartModel.getId())
                .productId(productModel.getId())
                .build();
        CartDetailModel cartDetailModel = CartDetailModel.builder()
                .id(key)
                .quantity(request.getQuantity())
                .price(productModel.getPrice()*request.getQuantity())
                .carts(cartModel)
                .products(productModel)
                .build();
        cartDetailRepository.save(cartDetailModel);
        cartModel.getCart_detail().add(cartDetailModel);
        cartModel.setTotal_amount(getTotalPrice(request.getCart_id()));
        cartRepository.save(cartModel);
    }
    public Float getTotalPrice(String cart_id) {
        Float totalPrice = 0f;
        List<CartDetailModel> cartDetailModels = cartDetailRepository.findByCartId(cart_id);
        for (CartDetailModel cartDetailModel : cartDetailModels) {
            totalPrice += cartDetailModel.getPrice();
        }
        return totalPrice;
    }
    public List<CartDetailResponse> getAllProductOfCart(String cart_id){
        return cartDetailRepository.findByCartId(cart_id).stream().map(this::convertCartDetail).toList();
    }
    public List<CartDetailResponse> deleteProductOfCart(CartDetailKey id){
        cartDetailRepository.deleteProductByCartId(id.getCartId(),id.getProductId());
        CartModel cart = cartRepository.findById(id.getCartId()).orElseThrow();
        cart.setTotal_amount(getTotalPrice(cart.getId()));
        return getAllProductOfCart(id.getCartId());
    }
    public void deleteCartDetailOfProduct(ProductModel product){
        List<CartDetailModel> cartDetail = cartDetailRepository.findByProducts(product);
        cartDetail.forEach(c -> cartDetailRepository.deleteProductByCartId(c.getId().getCartId(),c.getId().getProductId()));
    }
    public void deleteCartDetailOfCart(CartModel cart){
        List<CartDetailModel> cartDetail = cartDetailRepository.findByCarts(cart);
        cartDetailRepository.deleteAll(cartDetail);
    }
    public int count() {
        return (int) cartDetailRepository.count();
    }
    public void save(List<CartDetailModel> cartDetailModels) {
        cartDetailRepository.saveAll(cartDetailModels);
    }
}
