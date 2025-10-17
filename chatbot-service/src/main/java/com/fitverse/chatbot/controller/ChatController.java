package com.fitverse.chatbot.controller;

import com.fitverse.chatbot.dto.ChatMessageRequest;
import com.fitverse.chatbot.dto.ChatMessageResponse;
import com.fitverse.chatbot.service.ChatService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService service;

    public ChatController(ChatService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChatMessageResponse send(@Valid @RequestBody ChatMessageRequest request) {
        return service.sendUserMessage(request);
    }

    @GetMapping("/{userId}")
    public List<ChatMessageResponse> history(@PathVariable Long userId) {
        return service.history(userId);
    }
}
