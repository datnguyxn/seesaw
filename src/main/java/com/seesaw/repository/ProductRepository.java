package com.seesaw.repository;

import com.seesaw.model.ProductModel;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.domain.Specification;

public interface ProductRepository extends JpaRepository<ProductModel, String> {
    @Override
    Optional<ProductModel> findById(String id);
    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM products p WHERE p.brand LIKE %?1%"
            + " OR p.name LIKE %?1%"
            ,nativeQuery = true)
    List<ProductModel> search(String keyword);
    List<ProductModel> findAllByCategory_Id(String id);
    List<ProductModel> findAllByCollection_Id(String id);
//    Page<ProductModel> findAll(Specification<ProductModel> spec, Pageable pageRequest);
    List<ProductModel> findAll(Specification<ProductModel> spec, Sort sort);
}
