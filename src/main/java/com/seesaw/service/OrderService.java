package com.seesaw.service;

import com.seesaw.dto.request.OrderProduct;
import com.seesaw.dto.request.OrderRequest;
import com.seesaw.dto.response.OrderResponse;
import com.seesaw.model.InvoiceModel;
import com.seesaw.model.OrderModel;
import com.seesaw.model.UserModel;
import com.seesaw.repository.InvoiceRepository;
import com.seesaw.repository.OrderRepository;
import com.seesaw.repository.ProductRepository;
import com.seesaw.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    public static OrderModel toEntity(OrderRequest request) {
        return OrderModel.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .status("Đang giao")
                .build();
    }
    public OrderResponse toResponse(OrderModel order){
        return OrderResponse.builder()
                .id(order.getId())
                .name(order.getFirstName() + " " + order.getLastName())
                .email(order.getEmail())
                .phone(order.getPhone())
                .address(order.getAddress())
                .total_amount(order.getTotal_amount())
                .status(order.getStatus())
                .createdAt(order.getCreatedDate())
                .build();
    }
    @Transactional
    public OrderResponse addOrder(OrderRequest request){
        var user = userRepository.findById(request.getUser_id()).orElseThrow();
        Map<String, Integer> mapProducts = new HashMap<>();
        var products = request.getProducts().stream().map(product -> {
            var productEntity = productRepository.findById(product.getId()).orElseThrow();
            mapProducts.put(productEntity.getId(), product.getQuantity());
            return productEntity;
        }).toList();
        var order = toEntity(request);
        order.setCreatedDate(LocalDate.now());
        order.setTotal_amount(0d);
        var orderSaved = orderRepository.save(order);
        System.out.println(order);
        var orderDetails = products.stream().map(product -> {
            order.setTotal_amount(order.getTotal_amount() + product.getPrice() * mapProducts.get(product.getId()));
            return InvoiceModel.builder()
                    .orders(order)
                    .products(product)
                    .quantity(mapProducts.get(product.getId()))
                    .price(product.getPrice() * mapProducts.get(product.getId()))
                    .build();
        }).toList();
        invoiceRepository.saveAll(orderDetails);
        orderSaved.setUsers(user);
        return toResponse(orderSaved);
    }
    public Page<OrderResponse> getAllOrder(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
//        return orderRepository.findAll(pageRequest).stream().map(this::toResponse).toList();
        return orderRepository.findAll(pageRequest).map(this::toResponse);
    }
    public List<OrderResponse> get(int page, int size, String user_id) {
        PageRequest pageRequest = PageRequest.of(page, size);
        var user = userRepository.findById(user_id).orElseThrow();
        return orderRepository.findAllByUser_Id(user.getId(),pageRequest).stream().map(this::toResponse).toList();
    }
    public OrderResponse updateOrder(String id, OrderRequest request){
        var order = orderRepository.findById(id).orElseThrow();
        Map<String, Integer> mapProducts = new HashMap<>();
        var products = request.getProducts().stream().map(product -> {
            var productEntity = productRepository.findById(product.getId()).orElseThrow();
            mapProducts.put(productEntity.getId(), product.getQuantity());
            return productEntity;
        }).toList();
        var orderDetails = products.stream().map(product ->{
            var invoice = invoiceRepository.findByOrder_IdAndProduct_Id(id,product.getId());
            if(invoice != null){
                invoice.setQuantity(mapProducts.get(product.getId()));
                invoice.setPrice(product.getPrice() * mapProducts.get(product.getId()));
                order.setTotal_amount(getTotalPrice(id));

                return invoice;
            }
            order.setTotal_amount(order.getTotal_amount() + product.getPrice() * mapProducts.get(product.getId()));
            return InvoiceModel.builder()
                    .orders(order)
                    .products(product)
                    .quantity(mapProducts.get(product.getId()))
                    .price(product.getPrice() + mapProducts.get(product.getId()))
                    .build();
        }).toList();
        if(!request.getStatus().isEmpty()){
            order.setStatus(request.getStatus());
        }
        var orderSaved = orderRepository.save(order);
        invoiceRepository.saveAll(orderDetails);
        return toResponse(orderSaved);
    }
    public OrderResponse delete(String id){
        var order = orderRepository.findById(id).orElseThrow();
        invoiceRepository.deleteInvoiceByOrderId(id);
        orderRepository.deleteOrderId(id);
        return toResponse(order);
    }
    public void save(List<OrderModel> orderModels) {
        orderRepository.saveAll(orderModels);
    }
    public Double getTotalPrice(String order_id) {
        Double totalPrice = 0d;
        List<InvoiceModel> orderDetails = invoiceRepository.findByOrderId(order_id);
        for (InvoiceModel orderDetail : orderDetails) {
            totalPrice += orderDetail.getPrice();
        }
        return totalPrice;
    }
}
