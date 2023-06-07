package com.example.accountmanager;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "Account API", version = "1.0", description = "Documentation Account API v1.0")
)
public class AccountManager {

    public static void main(String[] args) {
        SpringApplication.run(AccountManager.class, args);
    }
}
