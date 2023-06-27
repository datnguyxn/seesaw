package com.seesaw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import java.util.Date;
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "token")
@Data
public class TokenModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(
            name = "token_id",
            columnDefinition = "VARCHAR(255)"
    )
    private String id;
    private String token;
    private Date expired;
    private Date revoke;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel users;
}
