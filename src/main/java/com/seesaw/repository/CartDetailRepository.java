package com.seesaw.repository;

import com.seesaw.model.CartDetailKey;
import com.seesaw.model.CartDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepository extends JpaRepository<CartDetailModel, CartDetailKey> {

}
