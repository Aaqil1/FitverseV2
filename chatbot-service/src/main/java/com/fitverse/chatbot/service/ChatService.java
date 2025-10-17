package com.fitverse.chatbot.service;

import com.fitverse.chatbot.dto.ChatMessageRequest;
import com.fitverse.chatbot.dto.ChatMessageResponse;
import com.fitverse.chatbot.entity.ChatMessage;
import com.fitverse.chatbot.exception.ChatHistoryNotFoundException;
import com.fitverse.chatbot.mapper.ChatMessageMapper;
import com.fitverse.chatbot.repository.ChatMessageRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatService {

    private final ChatMessageRepository repository;
    private final ChatMessageMapper mapper;

    public ChatService(ChatMessageRepository repository, ChatMessageMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ChatMessageResponse sendUserMessage(ChatMessageRequest request) {
        repository.save(new ChatMessage(request.getUserId(), "USER", request.getMessage()));
        ChatMessage botMessage = repository.save(new ChatMessage(request.getUserId(), "BOT", "Echo: " + request.getMessage()));
        return mapper.toResponse(botMessage);
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponse> history(Long userId) {
        List<ChatMessage> messages = repository.findByUserIdOrderByCreatedAtAsc(userId);
        if (messages.isEmpty()) {
            throw new ChatHistoryNotFoundException(userId);
        }
        return messages.stream().map(mapper::toResponse).collect(Collectors.toList());
    }
}
