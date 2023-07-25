package com.seesaw.service;

import com.seesaw.authentication.AuthenticationResponse;
import com.seesaw.authentication.EmailRequest;
import com.seesaw.configuration.ApplicationConfig;
import com.seesaw.dto.request.AddUserRequest;
import com.seesaw.dto.response.MessageResponse;
import com.seesaw.dto.response.UserResponse;
import com.seesaw.exception.UserNotFoundException;
import com.seesaw.model.*;
import com.seesaw.repository.TokenRepository;
import com.seesaw.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    private ApplicationConfig applicationConfig;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    @Modifying
    public void updatePassword(UserModel user) {
        user.setPassword(applicationConfig.passwordEncoder().encode(user.getPassword()));
    }

    public UserResponse covertUser(UserModel user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .gender(user.getGender())
                .contact(user.getContact())
                .address(user.getAddress())
                .avatar(user.getAvatar())
                .build();
    }


    public UserResponse findUserByToken(String token) {
        var userToken = tokenRepository.findByToken(token).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (userToken != null) {
            var user = userRepository.findByEmail(userToken.getUsers().getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
            return UserResponse.builder()
                    .id(user.getId())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .gender(user.getGender())
                    .contact(user.getContact())
                    .address(user.getAddress())
                    .avatar(user.getAvatar())
                    .build();
        } else {
            throw new UserNotFoundException("User not found");
        }
    }


    public MessageResponse updateUser(AddUserRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user != null) {
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setGender(request.getGender());
            user.setContact(request.getContact());
            user.setAddress(request.getAddress());
            user.setDate_updated(Date.from(java.time.Instant.now()));
            userRepository.save(user);
            return MessageResponse.builder()
                    .message("User updated")
                    .build();
        } else {
            return MessageResponse.builder()
                    .message("User not found")
                    .build();
        }
    }

    public List<UserResponse> getAllUsers() {
        var users = userRepository.findAll().stream().map(this::covertUser);
        return users.toList();
    }

    public MessageResponse deleteUser(EmailRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user != null) {
            var tokens = tokenRepository.findAllValidTokenByUser(user.getId());
            for (TokenModel token : tokens) {
                tokens.remove(token);
                tokenRepository.delete(token);
            }
            cartService.deleteCartOfUser(user);
            userRepository.delete(user);
            return MessageResponse.builder()
                    .message("User deleted")
                    .build();
        } else {
            return MessageResponse.builder()
                    .message("User not found")
                    .build();
        }
    }

    public MessageResponse deleteAllUsers() {
        var users = userRepository.findAll();
        if (!users.isEmpty()) {
            tokenRepository.deleteAll();
            users.forEach(user -> {
                cartService.deleteCartOfUser(user);
                userRepository.delete(user);
            });
            return MessageResponse.builder()
                    .message("All users deleted")
                    .build();
        } else {
            throw new UserNotFoundException("All Users not found");
        }
    }

    public boolean isExistUser(String email) {
        return userRepository.existsUserModelByEmail(email);
    }

    public void processOAuthPostLogin(String email, HttpServletResponse httpServletResponse) {
       var user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user != null) {
            user.setDate_updated(Date.from(java.time.Instant.now()));
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            authenticationService.revokeAllUserTokens(user);
            authenticationService.saveUserToken(user, jwtToken);
            authenticationService.createCookie(refreshToken, httpServletResponse);
            authenticationService.createCookieForRole(user.getRole().toString(), httpServletResponse);
            userRepository.save(user);
        }
    }

    public void processOAuthPostRegister(String email) {
        var user = UserModel.builder()
                .email(email)
                .provider(Provider.GOOGLE)
                .role(Role.USER)
                .avatar("/images/avtDefault.jpg")
                .date_created(Date.from(java.time.Instant.now()))
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        authenticationService.saveUserToken(savedUser, jwtToken);
        cartService.addCart(CartModel.builder()
                .user(savedUser)
                .total_amount(0.0F)
                .build());
    }

    public AuthenticationResponse findUserByEmail(String email) {
        String tokenValid = "";
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<TokenModel> tokens = tokenRepository.findAllValidTokenByUser(user.getId());
        for (TokenModel token : tokens) {
          tokenValid = token.getToken();
        }
        return AuthenticationResponse.builder()
                .accessToken(tokenValid)
                .build();
    }
}