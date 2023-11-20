package com.learning.attackerwebsite.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.learning")
public class AttackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttackerApplication.class, args);
    }
}
