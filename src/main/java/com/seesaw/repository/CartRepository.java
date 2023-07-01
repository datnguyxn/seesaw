package com.seesaw.repository;

import com.seesaw.model.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartModel, String> {
    Optional<CartModel> findByUserId(String userId);
}
