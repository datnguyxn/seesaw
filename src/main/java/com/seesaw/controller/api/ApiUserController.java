package com.seesaw.controller.api;

import com.seesaw.dto.request.AddUserRequest;
import com.seesaw.dto.response.MessageResponse;
import com.seesaw.dto.response.UserResponse;
import com.seesaw.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/update")
    public ResponseEntity<MessageResponse> update(@RequestBody  AddUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(request));
    }

    @PostMapping("/get-all-user")
    public ResponseEntity<List<UserResponse>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
