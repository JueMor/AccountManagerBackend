package com.example.accountmanager.payload.request;

import com.example.accountmanager.datatypes.Address;
import com.example.accountmanager.datatypes.Name;
import com.example.accountmanager.role.Role;

import java.time.LocalDate;
import java.util.Set;

public record UserRequest(
        Set<Role> roles,
        String username,
        Name name,
        Address address,
        String email,
        LocalDate dob,
        String phoneNumber,
        String password
) {
}
