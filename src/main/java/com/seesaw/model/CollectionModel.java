package com.seesaw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Collection;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "collections")
@Data
public class CollectionModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(
            name = "id_collection",
            columnDefinition = "VARCHAR(255)"
    )
    private String id;

    @Column(name = "name_collection")
    private String name;

    private String description;

    @OneToMany(mappedBy = "id_collection",cascade = CascadeType.ALL)
    private Collection<ProductModel> products;
}
