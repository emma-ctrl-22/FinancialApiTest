package com.example.financialapiapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.financialapiapplication.model")
@EnableJpaRepositories("com.example.financialapiapplication.repository")
public class FinancialApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialApiApplication.class, args);
    }

}
