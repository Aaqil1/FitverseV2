package com.fitverse.chatbot.exception;

public class ChatHistoryNotFoundException extends RuntimeException {

    public ChatHistoryNotFoundException(Long userId) {
        super("No chat history found for user %d".formatted(userId));
    }
}
