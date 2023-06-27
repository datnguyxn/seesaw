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
@Table(name = "users")
@Data
public class UserModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(
            name = "user_id",
            columnDefinition = "VARCHAR(255)"
    )
    private String id;
    private String firstname;
    private String lastname;
    private String gender;
    private String contact;
    private String email;
    private String password;
    private String avatar;
    private Date date_created;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private Collection<TokenModel> tokens;

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private Collection<OrderModel> orders;

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private Collection<FeedbackModel> feedbacks;

}
