package com.seesaw.repository;

import com.seesaw.model.InvoiceModel;
import com.seesaw.model.ProductModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<InvoiceModel, String> {
    List<InvoiceModel> findByProducts(ProductModel product);
    @Query(value = "SELECT * FROM invoices WHERE order_id = ?1", nativeQuery = true)
    List<InvoiceModel> findByOrderId(String order_id);
    @Query(value = "SELECT * FROM invoices WHERE order_id = ?1 AND product_id = ?2", nativeQuery = true)
    InvoiceModel findByOrder_IdAndProduct_Id(String order_id, String product_id);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM invoices WHERE order_id = ?1",nativeQuery = true)
    void deleteInvoiceByOrderId(String order_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM invoices WHERE product_id = ?1", nativeQuery = true)
    void deleteProductOfInvoice(String product_id);
}
