package com.seesaw.service;

import com.seesaw.configuration.ApplicationConfig;
import com.seesaw.exception.UserNotFoundException;
import com.seesaw.model.Role;
import com.seesaw.model.UserModel;
import com.seesaw.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

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

}