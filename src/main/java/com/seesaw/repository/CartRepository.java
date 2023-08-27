package com.seesaw.repository;

import com.seesaw.model.CartModel;
import com.seesaw.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartModel, String> {
    Optional<CartModel> findById(String id);
    Optional<CartModel> findByUser(UserModel user);

}
