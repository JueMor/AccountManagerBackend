package com.example.accountmanager.controllers;

import com.example.accountmanager.payload.request.AuthoRequest;
import com.example.accountmanager.payload.response.AuthoResponse;
import com.example.accountmanager.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/account/api/autho")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService){
        this.authorizationService = authorizationService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthoResponse> getAuthorization(@RequestBody long userId) {
        AuthoResponse response = authorizationService.getAuthorization(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAuthorization(@RequestBody AuthoRequest request) {
        authorizationService.setAuthorization(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
