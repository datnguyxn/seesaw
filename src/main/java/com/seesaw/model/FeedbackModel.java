package com.seesaw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
class FeedbackKey implements Serializable{
    @Column(name = "id_user")
    String userId;
    @Column(name = "id_product")
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
    @JoinColumn(name = "id_user")
    private UserModel users;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "id_product")
    private ProductModel products;

    private String note;
}
