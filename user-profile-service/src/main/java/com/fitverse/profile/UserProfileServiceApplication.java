package com.fitverse.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fitverse")
public class UserProfileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserProfileServiceApplication.class, args);
    }
}
