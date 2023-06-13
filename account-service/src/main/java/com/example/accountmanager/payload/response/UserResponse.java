package com.example.accountmanager.payload.response;

import com.example.accountmanager.datatypes.Address;
import com.example.accountmanager.datatypes.Name;
import com.example.accountmanager.role.Role;

import java.time.LocalDate;
import java.util.Set;

public record UserResponse(long accountId,
                           Set<Role> roles,
                           String username,
                           Name name,
                           Address address,
                           String email,
                           LocalDate dob,
                           String phoneNumber) { }