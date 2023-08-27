package com.seesaw.repository;

import com.seesaw.model.CartDetailModel;
import com.seesaw.model.CartModel;
import com.seesaw.model.ProductModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetailModel, String> {
    List<CartDetailModel> findByProducts(ProductModel product);
    List<CartDetailModel> findByCarts(CartModel cart);
    @Query(value = "SELECT * FROM cart_detail WHERE cart_id = ?1", nativeQuery = true)
    List<CartDetailModel> findByCartId(String cart_id);
    @Query(value = "SELECT * FROM cart_detail WHERE product_id = ?1", nativeQuery = true)
    List<CartDetailModel> findByProductId(String product_id);
    @Query(value = "SELECT * FROM cart_detail WHERE product_id = ?1 AND cart_id = ?2", nativeQuery = true)
    CartDetailModel findByProductIdAndCartId(String product_id, String cart_id);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cart_detail WHERE product_id = ?1 AND cart_id = ?2", nativeQuery = true)
    void deleteProductOfCart(String product_id, String cart_id);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cart_detail WHERE product_id = ?1", nativeQuery = true)
    void deleteProductOfCartDetail(String product_id);
}
