package com.example.accountmanager.mapper;

import com.example.accountmanager.model.account.authorized.Authorized;
import com.example.accountmanager.payload.response.AuthoResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthoMapper {
    public AuthoResponse toResponse (Authorized authorized){
        return new AuthoResponse(
                authorized.isAccountNonExpired(),
                authorized.isAccountNonLocked(),
                authorized.isCredentialsNonExpired(),
                authorized.isEnabled()
        );
    }
}
