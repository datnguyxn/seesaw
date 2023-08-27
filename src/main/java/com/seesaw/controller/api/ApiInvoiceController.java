package com.seesaw.controller.api;

import com.seesaw.dto.response.InvoiceResponse;
import com.seesaw.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoices")
public class ApiInvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getAllProductOfOrder(@PathVariable String id){
        return ResponseEntity.ok().body(invoiceService.getAllProductOfOrder(id));
    }
}
