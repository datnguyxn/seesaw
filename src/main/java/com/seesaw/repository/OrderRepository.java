package com.seesaw.repository;

import com.seesaw.model.OrderModel;
import com.seesaw.model.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderModel, String> {
    Optional<OrderModel> findByEmail(String email);
    List<OrderModel> findByUsers(UserModel user);
}
