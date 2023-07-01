package com.seesaw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Collection;
import java.util.Date;

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
            name = "order_id",
            columnDefinition = "VARCHAR(255)"
    )
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String zipCode;
    private String optional;
    private Date date_created;
    private Date date_updated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel users;

    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL)
    private Collection<InvoiceModel> invoices;
}
