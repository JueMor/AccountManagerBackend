package com.example.accountmanager.configurations;

import com.example.accountmanager.controllers.AuthController;
import com.example.accountmanager.datatypes.Address;
import com.example.accountmanager.datatypes.Name;
import com.example.accountmanager.model.account.User;
import com.example.accountmanager.model.account.UserRepository;
import com.example.accountmanager.payload.request.SignupRequest;
import com.example.accountmanager.role.ERole;
import com.example.accountmanager.role.Role;
import com.example.accountmanager.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Configuration
public class AccountManagerConfig {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Bean
    CommandLineRunner commandLineRunner(AuthController controller) {
        return args -> {
            for (ERole role : ERole.values()) {
                Optional<Role> roleExists = roleRepository.findByName(role);
                if (roleExists.isEmpty()) {
                    Role newRole = new Role(role);

                    roleRepository.save(newRole);
                }
            }

            Optional<Role> role = roleRepository.findByName(ERole.ROLE_ADMIN);

            if (role.isPresent()) {
                Optional<User> adminAccounts = userRepository.findByRoles(role.get());

                if (adminAccounts.isEmpty()) {
                    Name name = new Name("Jürgen", "Moroskow");
                    Address address = new Address("Admincity", "Adminstreet 14", 1413914);
                    Set<String> roles = new HashSet<>(Collections.singletonList("admin"));

                    SignupRequest request = new SignupRequest();
                    request.setRoles(roles);
                    request.setUsername("Admin");
                    request.setName(name);
                    request.setAddress(address);
                    request.setEmail("www.admin@basic.com");
                    request.setDob(LocalDate.of(1989, Month.SEPTEMBER, 27));
                    request.setPhoneNumber("081512345");
                    request.setPassword("Admin!");

                    controller.registerUser(request);

                    name = new Name("Max", "Mustermann");
                    address = new Address("Admincity1", "Adminstreet 15", 1413915);
                    roles = new HashSet<>(Collections.singletonList("admin"));

                    request = new SignupRequest();
                    request.setRoles(roles);
                    request.setUsername("Admin2");
                    request.setName(name);
                    request.setAddress(address);
                    request.setEmail("www.admin@basic2.com");
                    request.setDob(LocalDate.of(1989, Month.SEPTEMBER, 27));
                    request.setPhoneNumber("081512345");
                    request.setPassword("Admin!");

                    controller.registerUser(request);
                }
            }
        };
    }
}
