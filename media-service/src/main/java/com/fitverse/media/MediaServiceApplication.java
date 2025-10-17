package com.fitverse.media;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fitverse")
public class MediaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediaServiceApplication.class, args);
    }
}
