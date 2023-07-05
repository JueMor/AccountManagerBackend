package com.example.accountmanager.payload.request;

import jakarta.validation.constraints.NotNull;

public record AuthoRequest(
        @NotNull Integer userId,
        @NotNull Boolean accountNonExpired,
        @NotNull Boolean accountNonLocked,
        @NotNull Boolean credentialsNonExpired,
        @NotNull Boolean enabled
) { }
