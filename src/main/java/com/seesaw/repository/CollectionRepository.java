package com.seesaw.repository;

import com.seesaw.model.CollectionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CollectionRepository extends JpaRepository<CollectionModel, String> {
    Optional<CollectionModel> findByName(String name);
}
