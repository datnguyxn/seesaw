package com.seesaw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Data
public class OrderModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(
            columnDefinition = "VARCHAR(255)"
    )
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Double total_amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column
    @CreatedDate
    private LocalDate createdDate;
    private String status;

    @ManyToOne(targetEntity = UserModel.class)
    @JoinColumn(name = "user_id")
    private UserModel users;

    @OneToMany(mappedBy = "orders",targetEntity = InvoiceModel.class)
    private Set<InvoiceModel> invoices;

}
