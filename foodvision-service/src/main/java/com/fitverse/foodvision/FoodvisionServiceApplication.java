package com.fitverse.foodvision;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fitverse")
public class FoodvisionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodvisionServiceApplication.class, args);
    }
}
