package com.seesaw.repository;

import com.seesaw.model.TokenModel;
import com.seesaw.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenModel, String> {
    @Query("SELECT t FROM TokenModel t WHERE t.users.id = ?1 AND (t.expired = false OR t.revoked = false)")
    List<TokenModel> findAllValidTokenByUser(String id);

    Optional<TokenModel> findByToken(String token);



}