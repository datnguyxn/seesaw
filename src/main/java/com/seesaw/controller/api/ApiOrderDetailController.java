package com.seesaw.controller.api;

import com.seesaw.service.InvoiceService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/order-detail")
@RequiredArgsConstructor
public class ApiOrderDetailController {
    private final InvoiceService invoiceService ;

    @GetMapping("/order/{id}")
    public String orderDetail(
            @PathVariable(name = "id") String id,
            Model model
    ) {
        var order = invoiceService.getAllProductOfOrder(id);
        model.addAttribute("products",order);
        return "pages/admin/orderDetail";
    }
}
