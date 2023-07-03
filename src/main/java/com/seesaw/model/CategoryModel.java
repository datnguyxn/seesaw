package com.seesaw.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Collection;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
@Setter
@Getter
@ToString
public class CategoryModel {
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
    private String description;

    @OneToMany(mappedBy = "category", targetEntity = ProductModel.class)
    private Collection<ProductModel> products;
}
