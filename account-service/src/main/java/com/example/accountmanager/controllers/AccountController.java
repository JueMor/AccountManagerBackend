package com.example.accountmanager.controllers;

import com.example.accountmanager.model.account.User;
import com.example.accountmanager.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/account/api/user")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAccounts(){
        List<User> users = accountService.getAccounts();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> addNewAccount(@RequestBody User user){
        User newUser = accountService.addNewAccount(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<User> updateAccount(@RequestBody User user){
        User updateUser = accountService.updateAccount(user);

        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAccount(@PathVariable("email") String email){
        accountService.deleteAccount(email);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
