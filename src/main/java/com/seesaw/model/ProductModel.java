package com.seesaw.model;

import java.util.Collection;
import java.util.Date;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@Data
public class ProductModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(
            name = "id_product",
            columnDefinition = "VARCHAR(255)"
    )
    private String id;

    @Column(name = "name_product")
    private String name;
    private String description;
    private Float price;
    private Integer quantity;
    private String image_path;
    private Date date_created;
    private Date date_updated;

    @ManyToOne
    @JoinColumn(name = "id_collection")
    private CollectionModel id_collection;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private CategoryModel id_category;

    @OneToMany(mappedBy = "products")
    private Collection<FeedbackModel> feedbacks;

    @OneToMany(mappedBy = "products")
    private Collection<InvoiceModel> invoices;
}
