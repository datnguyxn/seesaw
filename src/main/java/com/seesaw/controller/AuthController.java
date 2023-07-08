package com.seesaw.controller;

import com.seesaw.auth.*;
import com.seesaw.dto.response.MailResponse;
import com.seesaw.dto.response.MessageResponse;
import com.seesaw.service.AuthenticationService;
import com.seesaw.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    private final LogoutService logoutService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Email @Valid RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request, HttpServletResponse httpServletResponse) {
        return ResponseEntity.ok(authenticationService.authenticate(request, httpServletResponse));
    }


    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @PostMapping("/send-mail")
    public ResponseEntity<MailResponse> sendMail(
            @RequestBody @Valid EmailRequest request) {
        return ResponseEntity.ok(authenticationService.sendMail(request));
    }

    @PostMapping("/verify-token")
    public ResponseEntity<MessageResponse> verifyToken(
            @RequestBody @Valid TokenRequest request, HttpSession session) {
        return ResponseEntity.ok(authenticationService.verifyToken(request, session));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(
            @RequestBody @Valid PasswordRequest request, HttpSession session) {
        return ResponseEntity.ok(authenticationService.changePassword(request, session));
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        logoutService.logout(request, response, null);
        return ResponseEntity.ok(MessageResponse.builder().message("Logout successful").build());
    }
}