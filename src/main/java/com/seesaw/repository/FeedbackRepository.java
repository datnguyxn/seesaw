package com.seesaw.repository;

import com.seesaw.model.FeedbackKey;
import com.seesaw.model.FeedbackModel;
import com.seesaw.model.ProductModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<FeedbackModel, FeedbackKey> {
    List<FeedbackModel> findByProducts(ProductModel product);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM feedbacks WHERE user_id = ?1 AND product_id = ?2", nativeQuery = true)
    void deleteByUserIdAndProductId(String userId, String productId);

    @Query(value = "SELECT * FROM feedbacks WHERE product_id = ?1",nativeQuery = true)
    List<FeedbackModel> findByProductId(String productID);
}
