package com.example.accountmanager.payload.request;

import com.example.accountmanager.datatypes.Address;
import com.example.accountmanager.datatypes.Name;
import com.example.accountmanager.role.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

public record UserRequest(
        Set<Role> roles,
        @NotBlank String username,
        @NotBlank Name name,
        @NotBlank Address address,
        @NotBlank @Size(max = 50) @Email String email,
        LocalDate dob,
        String phoneNumber,
        @NotBlank @Size(min = 6, max = 40) String password
) {
}
