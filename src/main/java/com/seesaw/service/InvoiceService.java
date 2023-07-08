package com.seesaw.service;

import com.seesaw.model.InvoiceModel;
import com.seesaw.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    public void save(List<InvoiceModel> invoiceModel) {
        invoiceRepository.saveAll(invoiceModel);
    }
}
