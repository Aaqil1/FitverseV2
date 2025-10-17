package com.fitverse.chatbot.service;

import com.fitverse.chatbot.dto.ChatMessageRequest;
import com.fitverse.chatbot.dto.ChatMessageResponse;
import com.fitverse.chatbot.entity.ChatMessage;
import com.fitverse.chatbot.exception.ChatHistoryNotFoundException;
import com.fitverse.chatbot.mapper.ChatMessageMapper;
import com.fitverse.chatbot.repository.ChatMessageRepository;
import com.fitverse.shared.ai.ChatAdapter;
import com.fitverse.shared.ai.dto.ChatCompletion;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatService {

    private final ChatMessageRepository repository;
    private final ChatMessageMapper mapper;
    private final ChatAdapter chatAdapter;
    private final StringRedisTemplate stringRedisTemplate;

    public ChatService(ChatMessageRepository repository, ChatMessageMapper mapper, ChatAdapter chatAdapter,
            StringRedisTemplate stringRedisTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.chatAdapter = chatAdapter;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public ChatMessageResponse sendUserMessage(ChatMessageRequest request) {
        repository.save(new ChatMessage(request.getUserId(), "USER", request.getMessage()));
        ChatCompletion completion = chatAdapter.chat("conversation:" + request.getUserId(), request.getMessage());
        ChatMessage botMessage = repository
                .save(new ChatMessage(request.getUserId(), "BOT", completion.getReply()));
        stringRedisTemplate.opsForValue().set("chat:presence:" + request.getUserId(), Instant.now().toString());
        ChatMessageResponse response = mapper.toResponse(botMessage);
        response.setEscalate(completion.shouldEscalate());
        return response;
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
