package com.seesaw.service;

import com.seesaw.dto.response.InvoiceResponse;
import com.seesaw.dto.response.OrderProductResponse;
import com.seesaw.model.InvoiceModel;
import com.seesaw.model.OrderModel;
import com.seesaw.model.ProductModel;
import com.seesaw.repository.InvoiceRepository;
import com.seesaw.repository.OrderRepository;
import com.seesaw.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    public InvoiceResponse toResponse(OrderModel order, List<OrderProductResponse> products){
        return InvoiceResponse.builder()
                .order_id(order.getId())
                .name(order.getUsers().getLastname() + " " + order.getUsers().getFirstname())
                .address(order.getAddress())
                .email(order.getEmail())
                .phone(order.getPhone())
                .total_amount(order.getTotal_amount())
                .status(order.getStatus())
                .createdAt(order.getCreatedDate())
                .products(products)
                .build();
    }
    public OrderProductResponse productResponse(String product_id, int quantity){
        var product = productRepository.findById(product_id).orElseThrow();
        return OrderProductResponse.builder()
                .image_path(product.getImage_path())
                .name(product.getName())
                .brand(product.getBrand())
                .quantity(quantity)
                .price(product.getPrice()*quantity)
                .build();
    }
    public InvoiceResponse getAllProductOfOrder(String order_id){
        var order = orderRepository.findById(order_id).orElseThrow();
        List<InvoiceModel> invoice = invoiceRepository.findByOrderId(order_id);
        var products = new ArrayList<OrderProductResponse>();
        invoice.forEach(item -> products.add(productResponse(item.getProducts().getId(), item.getQuantity())));
        return toResponse(order, products);
    }
    public void save(List<InvoiceModel> invoiceModel) {
        invoiceRepository.saveAll(invoiceModel);
    }

    public void deleteInvoicesOfProduct(ProductModel product){
        List<InvoiceModel> invoice = invoiceRepository.findByProducts(product);
        invoice.forEach(c -> invoiceRepository.deleteProductOfInvoice(product.getId()));
    }
}
