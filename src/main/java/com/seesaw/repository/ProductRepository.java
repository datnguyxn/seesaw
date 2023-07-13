package com.seesaw.repository;

import com.seesaw.model.CategoryModel;
import com.seesaw.model.CollectionModel;
import com.seesaw.model.ProductModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductModel, String> {
    @Override
    Optional<ProductModel> findById(String id);
    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM products p WHERE p.name = : %name%", nativeQuery = true)
    List<ProductModel> findByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM products p WHERE p.brand = : %brand%", nativeQuery = true)
    List<ProductModel> findByBrand(@Param("brand") String brand);
    List<ProductModel> findByCollection(CollectionModel collect);
    List<ProductModel> findByCategory(CategoryModel collect);
}
