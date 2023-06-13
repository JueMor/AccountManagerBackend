package com.example.accountmanager.configurations;

import com.example.accountmanager.controllers.UserController;
import com.example.accountmanager.datatypes.Address;
import com.example.accountmanager.datatypes.Name;
import com.example.accountmanager.model.account.User;
import com.example.accountmanager.model.account.UserRepository;
import com.example.accountmanager.payload.request.UserRequest;
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
    CommandLineRunner commandLineRunner(UserController controller) {
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
                List<User> adminAccounts = userRepository.findUsersByRolesIn(new HashSet<>(Collections.singletonList(role.get())));

                if (adminAccounts.isEmpty()) {
                    Name name = new Name("Jürgen", "Moroskow");
                    Address address = new Address("Admincity", "Adminstreet 14", 1413914);
                    Set<Role> roles = new HashSet<>(Collections.singletonList(role.get()));

                    UserRequest request = new UserRequest(
                            roles,
                            "Admin",
                            name,
                            address,
                            "www.admin@basic.com",
                            LocalDate.of(1989, Month.SEPTEMBER, 27),
                            "081512345",
                            "Admin!"
                    );

                    controller.addUser(request);

                    name = new Name("Max", "Mustermann");
                    address = new Address("Admincity1", "Adminstreet 15", 1413915);
                    roles = new HashSet<>(Collections.singletonList(role.get()));

                    request = new UserRequest(
                            roles,
                            "Admin2",
                            name,
                            address,
                            "www.admin@basic2.com",
                            LocalDate.of(1989, Month.SEPTEMBER, 27),
                            "081512345",
                            "Admin!"
                    );

                    controller.addUser(request);
                }
            }
        };
    }
}
