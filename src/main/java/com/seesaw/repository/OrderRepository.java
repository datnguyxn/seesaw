package com.seesaw.repository;

import com.seesaw.model.OrderModel;
import com.seesaw.model.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderModel, String> {
    Optional<OrderModel> findByEmail(String email);
    List<OrderModel> findByUsers(UserModel user);
    @Query(value = "SELECT * FROM `orders` WHERE user_id = ?1",nativeQuery = true)
    List<OrderModel> findAllByUser_Id(String user_id, PageRequest pageRequest);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM `orders` WHERE id = ?1", nativeQuery = true)
    void deleteOrderId(String id);
}
