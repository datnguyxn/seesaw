package com.seesaw.service;

import com.seesaw.model.CartDetailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seesaw.repository.CartDetailRepository;

import java.util.List;

@Service
public class CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;

    public void save(List<CartDetailModel> cartDetailModels) {
        cartDetailRepository.saveAll(cartDetailModels);
    }

    public Float getTotalPrice(List<CartDetailModel> cartDetailModels) {
        Float totalPrice = 0f;
        for (CartDetailModel cartDetailModel : cartDetailModels) {
            totalPrice += cartDetailModel.getPrice();
        }
        return totalPrice;
    }

    public int count() {
        return (int) cartDetailRepository.count();
    }
}
