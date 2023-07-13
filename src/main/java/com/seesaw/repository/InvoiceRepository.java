package com.seesaw.repository;

import com.seesaw.model.InvoiceKey;
import com.seesaw.model.InvoiceModel;
import com.seesaw.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceModel, InvoiceKey> {
    List<InvoiceModel> findByProducts(ProductModel product);
}
