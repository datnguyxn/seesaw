package com.seesaw.service;

import com.seesaw.dto.request.AddProductRequest;
import com.seesaw.dto.request.CartProduct;
import com.seesaw.dto.response.CartDetailResponse;
import com.seesaw.dto.response.CartProductResponse;
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
    public CartProductResponse productResponse(String product_id, Integer quantity){
        ProductModel product = productRepository.findById(product_id).orElseThrow();
        return CartProductResponse.builder()
                .id(product_id)
                .name(product.getName())
                .quantity(quantity)
                .price(product.getPrice())
                .image(product.getImage_path())
                .build();
    }
    public CartDetailResponse toResponse(CartModel cart, List<CartProductResponse> products){
        return CartDetailResponse.builder()
                .cart_id(cart.getId())
                .price(cart.getTotal_amount())
                .products(products)
                .build();
    }
    public void addToCart(AddProductRequest request) {
        CartModel cart = cartRepository.findById(request.getCart_id()).orElseThrow();
        ProductModel product = productRepository.findById(request.getProduct_id()).orElseThrow();
        if(!cartDetailRepository.findByCartId(request.getCart_id()).isEmpty() && !cartDetailRepository.findByProductId(request.getProduct_id()).isEmpty()){
            updateCart(cart,cartDetailRepository.findByProductIdAndCartId(request.getProduct_id(), request.getCart_id()),request.getQuantity(),product.getPrice());
        }else{
            CartDetailModel cartDetail = CartDetailModel.builder()
                    .quantity(request.getQuantity())
                    .price(product.getPrice()*request.getQuantity())
                    .carts(cart)
                    .products(product)
                    .build();
            cartDetailRepository.save(cartDetail);
            cart.getCart_detail().add(cartDetail);
            cart.setTotal_amount(getTotalPrice(request.getCart_id()));
            cartRepository.save(cart);
        }
    }
    public void updateCart(CartModel cart, CartDetailModel cartDetail, int quantity, double price){
        cartDetail.setQuantity(cartDetail.getQuantity()+quantity);
        cartDetail.setPrice(cartDetail.getPrice()+price*quantity);
        cartDetailRepository.save(cartDetail);
        cart.setTotal_amount(getTotalPrice(cart.getId()));
        cartRepository.save(cart);
    }
    public void updateCart(String cart_id, List<CartProduct> products){
        var cart = cartRepository.findById(cart_id).orElseThrow();
        for(CartProduct product : products){
            var cartDetail = cartDetailRepository.findByProductIdAndCartId(product.getId(), cart.getId());
            cartDetail.setQuantity(product.getQuantity());
            cartDetail.setPrice(product.getPrice()*product.getQuantity());
            cartDetailRepository.save(cartDetail);
        }
        cart.setTotal_amount(getTotalPrice(cart.getId()));
        cartRepository.save(cart);
    }
    public void updateQuantity(String cart_id, String product_id, int quantity){
        var cart = cartRepository.findById(cart_id).orElseThrow();
        var cartDetail = cartDetailRepository.findByProductIdAndCartId(product_id, cart_id);
        cartDetail.setQuantity(quantity);
        cartDetail.setPrice(cartDetail.getProducts().getPrice()*quantity);
        cartDetailRepository.save(cartDetail);
        cart.setTotal_amount(getTotalPrice(cart.getId()));
        cartRepository.save(cart);
    }
    public Double getTotalPrice(String cart_id) {
        Double totalPrice = 0d;
        List<CartDetailModel> cartDetailModels = cartDetailRepository.findByCartId(cart_id);
        for (CartDetailModel cartDetailModel : cartDetailModels) {
            totalPrice += cartDetailModel.getPrice();
        }
        return totalPrice;
    }
    public CartDetailResponse getAllProductOfCart(String cart_id){
        var products = cartDetailRepository.findByCartId(cart_id).stream().map(c -> productResponse(c.getProducts().getId(),c.getQuantity())).toList();
        if (products.isEmpty()){
            return CartDetailResponse.builder()
                    .cart_id(cart_id)
                    .price(0d)
                    .products(products)
                    .build();
        }
        var cart = cartRepository.findById(cart_id).orElseThrow();
        return toResponse(cart, products);

    }
    public CartDetailResponse deleteProductOfCart(String product_id, String cart_id){
        cartDetailRepository.deleteProductOfCart(product_id,cart_id);
        CartModel cart = cartRepository.findById(cart_id).orElseThrow();
        cart.setTotal_amount(getTotalPrice(cart.getId()));
        cartRepository.save(cart);
        return getAllProductOfCart(cart_id);
    }
    public void deleteCartDetailOfProduct(ProductModel product){
        List<CartDetailModel> cartDetail = cartDetailRepository.findByProducts(product);
        cartDetail.forEach(c -> cartDetailRepository.deleteProductOfCartDetail(product.getId()));
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
