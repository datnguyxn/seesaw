package com.seesaw.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seesaw.authentication.*;
import com.seesaw.configuration.TokenType;
import com.seesaw.dto.response.MailResponse;
import com.seesaw.dto.response.MessageResponse;
import com.seesaw.exception.PasswordNotMatchException;
import com.seesaw.exception.TokenNotFoundException;
import com.seesaw.exception.UserExistException;
import com.seesaw.exception.UserNotFoundException;
import com.seesaw.model.*;
import com.seesaw.repository.TokenRepository;
import com.seesaw.repository.UserRepository;
import com.seesaw.service.impl.MailServiceImpl;
import com.seesaw.utils.GenerateToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.seesaw.model.Mail;
import com.seesaw.model.UserModel;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MailServiceImpl mailServiceImpl;

    private String tokenToVerify;

    @Autowired
    private CartService cartService;

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse httpServletResponse) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        createCookie(refreshToken, httpServletResponse);
        createCookieForRole(user.getRole().name(), httpServletResponse);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String loginSuccess(String token) {
      var checkToken = tokenRepository.findByToken(token).orElseThrow(() -> new UserNotFoundException("Token not found"));
      var user = checkToken.getUsers();
        if (user.getRole().equals(Role.ADMIN)) {
            return "/admin/login";
        } else {
            return "/";
        }
    }

    public AuthenticationResponse register(RegisterRequest request) throws UserExistException {
        if (userRepository.existsUserModelByEmail(request.getEmail())) {
            throw new UserExistException("User already exist");
        }
        var user = UserModel.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .avatar("/images/avtDefault.jpg")
                .date_created(Date.from(java.time.Instant.now()))
                .date_updated(null)
                .role(Role.USER)
                .provider(Provider.LOCAL)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        cartService.addCart(CartModel.builder()
                .user(savedUser)
                .total_amount(0.0D)
                .build());
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    protected void saveUserToken(UserModel user, String jwtToken) {
        var token = TokenModel.builder()
                .users(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String email;


        if (authHeader == null || !authHeader.startsWith(TokenType.BEARER.getTokenType())) {
            return;
        }
        refreshToken = authHeader.substring(7);
        email = jwtService.extractEmail(refreshToken);
        if (email != null) {
            var user = this.userRepository.findByEmail(email)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }

        }
    }


    protected void revokeAllUserTokens(UserModel user) {
        var validToken = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validToken.isEmpty()) {
            return;
        }
        validToken.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validToken);
    }

    public MailResponse sendMail(EmailRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user == null) {
            return null;
        } else {
            tokenToVerify = GenerateToken.randomDigits(6);
            var mail = Mail.builder()
                    .to(user.getEmail())
                    .subject("Code to Reset Your Password")
                    .content(tokenToVerify)
                    .build();
            mailServiceImpl.sendEmail(mail, "mail-sender.html");
        }
        return MailResponse.builder()
                .message("Email sent successfully")
                .status(true)
                .build();
    }

    public MessageResponse verifyToken(TokenRequest request) {
        if (request.getToken().equals(tokenToVerify)) {
            return MessageResponse.builder()
                    .message("Token verified successfully")
                    .build();
        }
        throw new TokenNotFoundException("Token is not valid");
    }

    public MessageResponse changePassword(PasswordRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (request.getPassword().equals(request.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
            return MessageResponse.builder()
                    .message("Password changed successfully")
                    .build();
        } else {
            throw new PasswordNotMatchException("Password does not match");
        }
    }

    public MessageResponse checkEmail(EmailRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user != null) {
            return MessageResponse.builder()
                    .message("Email is valid")
                    .build();
        }
        throw new UserNotFoundException("User not found");
    }

    protected void createCookie(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        response.addCookie(cookie);
    }

    protected void createCookieForRole(String role, HttpServletResponse response) {
        Cookie cookie = new Cookie("role", role);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
    }
}
