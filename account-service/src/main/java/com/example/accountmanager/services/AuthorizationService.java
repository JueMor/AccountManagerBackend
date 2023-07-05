package com.example.accountmanager.services;

import com.example.accountmanager.mapper.AuthoMapper;
import com.example.accountmanager.model.account.authorized.Authorized;
import com.example.accountmanager.model.account.authorized.AuthorizedRepository;
import com.example.accountmanager.payload.request.AuthoRequest;
import com.example.accountmanager.payload.response.AuthoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final AuthorizedRepository authorizedRepository;
    private final AuthoMapper authoMapper;

    @Autowired
    public AuthorizationService(AuthorizedRepository authorizedRepository,
                                AuthoMapper authoMapper){
        this.authorizedRepository = authorizedRepository;
        this.authoMapper = authoMapper;
    }

    public void setAuthorization(AuthoRequest request){
        Authorized authorized = authorizedRepository
                .getAuthorizedByUserId(request.userId()).orElseThrow(
                        () -> new IllegalStateException("Authorization information for user not found")
                );

        authorized.setAccountNonExpired(request.accountNonExpired());
        authorized.setAccountNonLocked(request.accountNonLocked());
        authorized.setCredentialsNonExpired(request.credentialsNonExpired());
        authorized.setEnabled(request.enabled());
    }

    public AuthoResponse getAuthorization(long id){
        Authorized authorized = authorizedRepository
                .getAuthorizedByUserId(id).orElseThrow(
                        () -> new IllegalStateException("Authorization information for user not found")
                );

        return authoMapper.toResponse(authorized);
    }
}
