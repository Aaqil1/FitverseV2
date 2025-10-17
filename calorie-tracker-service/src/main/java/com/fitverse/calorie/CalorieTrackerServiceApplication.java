package com.fitverse.calorie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fitverse")
public class CalorieTrackerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalorieTrackerServiceApplication.class, args);
    }
}
