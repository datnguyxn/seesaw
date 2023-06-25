package com.seesaw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(
            name = "id",
            columnDefinition = "VARCHAR(255)"
    )
    private String id;

    @Column()
    private String username;

    @Column()
    private String password;

    @Column()
    private String email;

    @Column()
    private String contact;

    @Enumerated(EnumType.STRING)
    private Role role;

}
