package com.example.accountmanager.payload.response;

import jakarta.validation.constraints.NotNull;

public record AuthoResponse(
        @NotNull Boolean accountNonExpired,
        @NotNull Boolean accountNonLocked,
        @NotNull Boolean credentialsNonExpired,
        @NotNull Boolean enabled
) {
}
