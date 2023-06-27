package com.seesaw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
class FeedbackKey implements Serializable{
    @Column(name = "user_id")
    String userId;
    @Column(name = "product_id")
    String productId;
}

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feedbacks")
@Data
public class FeedbackModel {
    @EmbeddedId
    private FeedbackKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserModel users;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private ProductModel products;

    private String note;
}
