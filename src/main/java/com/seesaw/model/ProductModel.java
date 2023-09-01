package com.seesaw.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class ProductModel {
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
    private String name;
    private String brand;
    private String description;
    private Double price;
    private int quantity;
    private String image_path;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column
    @CreatedDate
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "updated_date")
    @LastModifiedDate
    private LocalDate updatedDate;

    @ManyToOne(targetEntity = CollectionModel.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "collection_id")
    private CollectionModel collection;

    @ManyToOne(targetEntity = CategoryModel.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryModel category;

    @OneToMany(mappedBy = "products",targetEntity = FeedbackModel.class)
    private Set<FeedbackModel> feedbacks;

    @OneToMany(mappedBy = "products", targetEntity = InvoiceModel.class)
    private Set<InvoiceModel> invoices;

    @OneToMany(mappedBy = "products", targetEntity = CartDetailModel.class)
    private Set<CartDetailModel> cart_detail;
}
