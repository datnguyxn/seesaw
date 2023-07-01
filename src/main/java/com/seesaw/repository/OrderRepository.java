package com.seesaw.repository;

import com.seesaw.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderModel, String> {
    Optional<OrderModel> findByEmail(String email);
//
//    Optional<OrderModel> updateOrderModelBy(String email);
//
}
