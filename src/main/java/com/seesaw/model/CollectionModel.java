package com.seesaw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Collection;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "collections")
@Setter
@Getter
@ToString
public class CollectionModel {
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

    @OneToMany(mappedBy = "collection",targetEntity = ProductModel.class)
    private Collection<ProductModel> products;
}
