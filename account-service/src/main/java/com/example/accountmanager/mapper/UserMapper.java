package com.example.accountmanager.mapper;

import com.example.accountmanager.payload.request.UserRequest;
import com.example.accountmanager.payload.response.UserResponse;
import com.example.accountmanager.model.account.User;
import org.springframework.stereotype.Service;

@Service
public final class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getRoles(),
                user.getUsername(),
                user.getName(),
                user.getAddress(),
                user.getEmail(),
                user.getDob(),
                user.getPhoneNumber()
        );
    }

    public User toUser(UserRequest request) {
        return new User(
                request.username(),
                request.name(),
                request.address(),
                request.email(),
                request.dob(),
                request.phoneNumber(),
                request.password()
        );
    }
}
