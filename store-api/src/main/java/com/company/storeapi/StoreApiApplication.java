package com.company.storeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class StoreApiApplication{

    public static void main(String[] args) {
        SpringApplication.run(StoreApiApplication.class, args);
    }

}

