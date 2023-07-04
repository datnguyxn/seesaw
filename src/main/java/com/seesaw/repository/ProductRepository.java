package com.seesaw.repository;

import com.seesaw.model.CollectionModel;
import com.seesaw.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductModel, String> {
    @Override
    Optional<ProductModel> findById(String id);
    Optional<ProductModel> findByName(String name);
    Optional<ProductModel> findByBrand(String brand);
    List<ProductModel> findByCollection(CollectionModel collect);
}
