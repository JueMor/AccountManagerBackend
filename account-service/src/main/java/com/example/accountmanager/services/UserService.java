package com.example.accountmanager.services;

import com.example.accountmanager.datatypes.Name;
import com.example.accountmanager.mapper.UserMapper;
import com.example.accountmanager.model.account.User;
import com.example.accountmanager.model.account.UserRepository;
import com.example.accountmanager.model.account.authorized.Authorized;
import com.example.accountmanager.model.account.authorized.AuthorizedRepository;
import com.example.accountmanager.payload.request.UserRequest;
import com.example.accountmanager.role.ERole;
import com.example.accountmanager.role.Role;
import com.example.accountmanager.role.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final AuthorizedRepository authorizedRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       AuthorizedRepository authorizedRepository,
                       RoleRepository roleRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorizedRepository = authorizedRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getAccountById(long id) {
        Optional<User> account = userRepository.findById(id);
        return account.orElse(null);
    }

    public User addUser(UserRequest request) throws IllegalStateException {
        logger.info("Adding new user");
        if (userRepository.existsByUsername(request.username())) {
            logger.info("Username: " + request.username() + ", already taken!");
            throw new IllegalStateException("Username already taken!");
        }
        if (userRepository.existsByEmail(request.email())) {
            logger.info("Email: " + request.email() + ",already taken");
            throw new IllegalStateException("Email already taken!");
        }

        User user = userMapper.toUser(request);
        if (user.getRoles().isEmpty()) {
            Role role = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new IllegalArgumentException("Role user not found!"));
            user.setRoles(new HashSet<>(Collections.singleton(role)));
        }

        //Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Authorized authorized = new Authorized();
        user.setAuthorized(authorized);
        authorized.setUser(user);

        userRepository.save(user);
        authorizedRepository.save(authorized);
        logger.info("New user has been added");

        return user;
    }

    public void deleteUser(String email) {
        Optional<User> deleteAccount = userRepository.findByEmail(email);
        if (deleteAccount.isEmpty()) {
            throw new IllegalStateException(
                    "User with email \"" + email + "\" does not exist"
            );
        }
        userRepository.deleteById(deleteAccount.get().getId());
    }

    @Transactional
    public User updateUser(UserRequest request) {
        User user = userMapper.toUser(request);
        User updateUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalStateException(
                        "User with email " + user.getEmail() + " does not exist"
                ));

        setRole(user, updateUser);

        setName(user, updateUser);
        setUserName(user, updateUser);
        setEmail(user, updateUser);
        setAddress(user, updateUser);
        setDateOfBirth(user, updateUser);
        setPhoneNumber(user, updateUser);

        return updateUser;
    }

    private void setRole(User user, User updateUser) {
        if (user.getRoles().isEmpty()) {
            return;
        }

        updateUser.setRoles(user.getRoles());
    }

    private void setName(User user, User updateUser) {
        if (user.getName().equals(updateUser.getName())) {
            return;
        }

        if (user.getName().getFirstName() != null &&
                user.getName().getLastName() != null &&
                user.getName().getFirstName().length() > 0 &&
                user.getName().getLastName().length() > 0) {
            Name newName = new Name(user.getName().getFirstName(), user.getName().getLastName());

            updateUser.setName(newName);
        } else {
            throw new IllegalStateException("name incorrect");
        }
    }

    private void setUserName(User user, User updateUser) {
        if (user.getUsername().equals("") || user.getUsername().equals(updateUser.getUsername())) {
            return;
        }

        if (user.getUsername() != null &&
                user.getUsername().length() > 0) {
            Optional<User> userByUserName = userRepository.findByUsername(user.getUsername());
            if (userByUserName.isPresent()) {
                if (userByUserName.get().getId() != updateUser.getId()) {
                    throw new IllegalStateException("username taken");
                }
            }
            updateUser.setUsername(user.getUsername());
        }
    }

    private void setAddress(User user, User updateUser) {
        if (user.getAddress().equals(updateUser.getAddress())) {
            return;
        }

        updateUser.setAddress(user.getAddress());
    }

    private void setEmail(User user, User updateUser) {
        if (user.getEmail().equals("") || user.getEmail().equals(updateUser.getEmail())) {
            return;
        }

        if (user.getEmail() != null &&
                user.getEmail().length() > 0) {
            Optional<User> userByMail = userRepository.findByEmail(user.getEmail());
            if (userByMail.isPresent()) {
                if (userByMail.get().getId() != updateUser.getId()) {
                    throw new IllegalStateException("email taken");
                }
            }
            updateUser.setEmail(user.getEmail());
        }
    }

    private void setDateOfBirth(User user, User updateUser) {
        updateUser.setDob(user.getDob());
    }

    private void setPhoneNumber(User user, User updateUser) {
        updateUser.setPhoneNumber(user.getPhoneNumber());
    }

    private void setPassword(User user, User updateUser) {
        updateUser.setPassword(user.getPassword());
    }


}
