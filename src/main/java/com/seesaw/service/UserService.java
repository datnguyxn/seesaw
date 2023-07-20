package com.seesaw.service;

import com.seesaw.configuration.ApplicationConfig;
import com.seesaw.dto.request.AddUserRequest;
import com.seesaw.dto.response.MessageResponse;
import com.seesaw.dto.response.UserResponse;
import com.seesaw.exception.UserNotFoundException;
import com.seesaw.model.UserModel;
import com.seesaw.repository.TokenRepository;
import com.seesaw.repository.UserRepository;
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

}