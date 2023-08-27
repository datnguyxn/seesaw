package com.seesaw.service;

import com.seesaw.configuration.TokenType;
import com.seesaw.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;
        if (authorizationHeader == null || !authorizationHeader.startsWith(TokenType.BEARER.getTokenType())) {
            return;
        }
        jwt = authorizationHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            authenticationService.createCookie("null", response);
            authenticationService.createCookieForRole("null", response);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }

    }
}
