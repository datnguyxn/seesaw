package com.seesaw.controller.api;

import com.seesaw.authentication.AuthenticationResponse;
import com.seesaw.authentication.EmailRequest;
import com.seesaw.authentication.TokenRequest;
import com.seesaw.dto.request.AddTokenRequest;
import com.seesaw.dto.request.AddUserRequest;
import com.seesaw.dto.response.MessageResponse;
import com.seesaw.dto.response.UserResponse;
import com.seesaw.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ApiUserController {

    @Autowired
    private UserService userService;

    @PostMapping("/get-user")
    public ResponseEntity<UserResponse> getUser(@RequestBody String token) {
        return ResponseEntity.ok(userService.findUserByToken(token));
    }

    @PostMapping("/get-cart")
    public String getCart(@RequestBody String token) {
        return userService.findCartIdByToken(token);
    }

    @PostMapping("/get-user-by-email")
    public ResponseEntity<AuthenticationResponse> getUserByEmail(@RequestBody String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @PutMapping("/update")
    public ResponseEntity<MessageResponse> update(@RequestBody AddUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(request));
    }

    @PostMapping("/get-all-user")
    public ResponseEntity<List<UserResponse>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable("id") String request) {
        return ResponseEntity.ok().body(userService.deleteUserById(request));
    }

    @DeleteMapping("delete-all-user")
    public ResponseEntity<MessageResponse> deleteAllUser() {
        return ResponseEntity.ok(userService.deleteAllUsers());
    }
}
