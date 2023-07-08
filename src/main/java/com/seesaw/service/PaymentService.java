package com.seesaw.service;

import com.seesaw.dto.request.AddPaymentRequest;
import com.seesaw.dto.response.MailResponse;
import com.seesaw.dto.response.MessageResponse;
import com.seesaw.exception.UserNotFoundException;
import com.seesaw.model.Mail;
import com.seesaw.model.TokenModel;
import com.seesaw.model.UserModel;
import com.seesaw.repository.OrderRepository;
import com.seesaw.repository.TokenRepository;
import com.seesaw.repository.UserRepository;
import com.seesaw.service.impl.MailServiceImpl;
import com.seesaw.utils.GenerateToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.upperCase;

@Service
public class PaymentService {

    @Autowired
    private MailServiceImpl mailServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private OrderRepository orderRepository;

    public MessageResponse payment(AddPaymentRequest request, HttpServletRequest res, HttpServletResponse rep) {
        var tokenAuth = res.getHeader("Authorization").split(" ")[1];
        TokenModel tokenUser = tokenRepository.findByToken(tokenAuth).orElseThrow(() -> new UserNotFoundException("User not found"));
        sendMail(tokenUser);
        return MessageResponse.builder()
//                .message(upperCase(request.getCardName()))
//                .message(String.valueOf(request.getCardNumber()))
//                .message(request.getMonth())
//                .message(request.getYear())
//                .message(request.getCvv())
                .message("Payment Successful")
                .build();
    }

    private void sendMail(TokenModel token) {

        UserModel user = userRepository.findUserModelById(token.getUsers().getId()).orElseThrow(() -> new UserNotFoundException("User not found"));

//        System.out.println(user);

        if (user.getEmail() == null) {
            throw new UserNotFoundException("User not found");
        } else {
            var order = orderRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
            var mail = Mail.builder()
                    .to(user.getEmail())
                    .subject("Your order details")
                    .content(GenerateToken.randomDigits(4))
                    .date(new Date())
                    .price(123412421)
                    .address(order.getAddress())
                    .product("haha")
                    .build();
            mailServiceImpl.sendEmail(mail, "mail-success.html");
        }
    }
}
