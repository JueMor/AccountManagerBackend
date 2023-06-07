package org.example;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.util.function.Function;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "API Gateway", version = "1.0", description = "Documentation API Gateway v1.0"))
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("account", r -> r.host("*")
                        .and()
                        .path("/account/api/**")
                        .filters(myCorsFilter())
                        .uri("lb://account-service"))
                .route("openapi", r -> r.host("*")
                        .and()
                        .path("/account/v3/api-docs")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(myCorsFilter())
                        .uri("lb://account-service"))
                .build();
    }

    @Bean
    Function<GatewayFilterSpec, UriSpec> myCorsFilter() {
        return f -> f
                .setResponseHeader("Access-Control-Allow-Origin", "*")
                .setResponseHeader("Access-Control-Allow-Methods", "*")
                .setResponseHeader("Access-Control-Expose-Headers", "*");
    }
}