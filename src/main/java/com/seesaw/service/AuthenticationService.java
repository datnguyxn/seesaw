package com.seesaw.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seesaw.auth.*;
import com.seesaw.configuration.TokenType;
import com.seesaw.exception.UserNotFoundException;
import com.seesaw.model.*;
import com.seesaw.repository.TokenRepository;
import com.seesaw.repository.UserRepository;
import com.seesaw.service.impl.MailServiceImpl;
import com.seesaw.utils.GenerateToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MailServiceImpl mailServiceImpl;
    private String tokenToVerify;
    private String email;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getRole().equals(Role.ADMIN)) {
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserModel.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .gender(request.getGender())
                .contact(request.getContact())
                .avatar(request.getAvatar())
                .date_created(Date.from(java.time.Instant.now()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(UserModel user, String jwtToken) {
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

    private void revokeAllUserTokens(UserModel user) {
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

    public MailResponse sendMail(ForgotPassword request, HttpSession session) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user == null) {
            return null;
        } else {
            tokenToVerify = GenerateToken.randomDigits(6);
            System.out.println(tokenToVerify);
            var mail = Mail.builder()
                    .to(user.getEmail())
                    .subject("Code to Reset Your Password")
                    .content(tokenToVerify)
                    .build();
            mailServiceImpl.sendEmail(mail);
        }
        return MailResponse.builder()
                .message("Email sent successfully")
                .status(true)
                .build();
    }

    public MessageResponse verifyToken(CheckToken request, HttpSession session) {
        if (request.getToken().equals(tokenToVerify)) {
            return MessageResponse.builder()
                    .message("Token verified successfully")
                    .build();
        }
        return MessageResponse.builder()
                .message("Token not verified")
                .build();
    }

    public MessageResponse changePassword(ConfirmPassword request, HttpSession session) {
        var user = userRepository.findByEmail((String) session.getAttribute("email")).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getPassword().equals(request.getPassword())) {
            return MessageResponse.builder()
                    .message("Password cannot be the same as the old password")
                    .build();
        }
        if (request.getPassword().equals(request.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
            return MessageResponse.builder()
                    .message("Password changed successfully")
                    .build();
        } else {
            return MessageResponse.builder()
                    .message("Password does not match")
                    .build();
        }
    }
}
