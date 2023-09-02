package com.seesaw.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Collection;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
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
    private String image;
    @OneToMany(mappedBy = "category", targetEntity = ProductModel.class,fetch = FetchType.EAGER)
    private Set<ProductModel> products;
}
