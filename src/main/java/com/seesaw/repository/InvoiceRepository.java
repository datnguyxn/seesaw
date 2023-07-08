package com.seesaw.repository;

import com.seesaw.model.InvoiceKey;
import com.seesaw.model.InvoiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<InvoiceModel, InvoiceKey> {
}
