package com.fitverse.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fitverse")
public class ChatbotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatbotServiceApplication.class, args);
    }
}
