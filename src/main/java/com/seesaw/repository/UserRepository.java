package com.seesaw.repository;
import com.seesaw.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, String> {
    Optional<UserModel> findUserModelById(String id);
    Optional<UserModel> findUserModelByEmail(String email);
    Optional<UserModel> findByEmail(String email);
}
