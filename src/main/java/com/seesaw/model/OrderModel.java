package com.seesaw.model;

import jakarta.persistence.*;
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
            name = "id_order",
            columnDefinition = "VARCHAR(255)"
    )
    private String id;
    private String name;
    private String delivery_address;
    private String total_amount;
    private String note;
    private Integer status;
    private Date date_created;
    private Date date_updated;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserModel id_user;

    @OneToMany(mappedBy = "orders")
    private Collection<InvoiceModel> invoices;
}
