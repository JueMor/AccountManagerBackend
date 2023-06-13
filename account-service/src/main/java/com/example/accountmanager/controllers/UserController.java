package com.example.accountmanager.controllers;

import com.example.accountmanager.payload.request.UserRequest;
import com.example.accountmanager.payload.response.UserResponse;
import com.example.accountmanager.mapper.UserMapper;
import com.example.accountmanager.model.account.User;
import com.example.accountmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/account/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAccounts(){
        List<UserResponse> userResponses = userService
                .getUsers()
                .stream()
                .map(userMapper::toResponse)
                .toList();

        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> addAccount(@RequestBody UserRequest request){
        UserResponse userResponse = userMapper.toResponse(userService.addUser(request));

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateAccount(@RequestBody UserRequest request){
        UserResponse userResponse = userMapper.toResponse(userService.updateUser(request));

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAccount(@PathVariable("email") String email){
        userService.deleteUser(email);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
