package com.fitverse.chatbot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fitverse.chatbot.dto.ChatMessageRequest;
import com.fitverse.chatbot.entity.ChatMessage;
import com.fitverse.chatbot.exception.ChatHistoryNotFoundException;
import com.fitverse.chatbot.mapper.ChatMessageMapper;
import com.fitverse.chatbot.repository.ChatMessageRepository;
import com.fitverse.shared.ai.ChatAdapter;
import com.fitverse.shared.ai.dto.ChatCompletion;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private ChatMessageRepository repository;

    private final ChatMessageMapper mapper = Mappers.getMapper(ChatMessageMapper.class);

    @Mock
    private ChatAdapter chatAdapter;

    @Mock
    private StringRedisTemplate stringRedisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    private ChatService service;

    @BeforeEach
    void setUp() {
        service = new ChatService(repository, mapper, chatAdapter, stringRedisTemplate);
    }

    @Test
    void sendUserMessagePersistsBotReply() {
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(repository.save(any(ChatMessage.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(chatAdapter.chat(any(), any())).thenReturn(new ChatCompletion("Hello there", false));
        ChatMessageRequest request = new ChatMessageRequest();
        request.setUserId(1L);
        request.setMessage("Hello");

        assertThat(service.sendUserMessage(request).getContent()).isEqualTo("Hello there");
        verify(repository, times(2)).save(any(ChatMessage.class));
        verify(valueOperations).set(any(), any());
    }

    @Test
    void historyThrowsWhenEmpty() {
        when(repository.findByUserIdOrderByCreatedAtAsc(1L)).thenReturn(Collections.emptyList());
        assertThrows(ChatHistoryNotFoundException.class, () -> service.history(1L));
    }

    @Test
    void historyReturnsMappedMessages() {
        when(repository.findByUserIdOrderByCreatedAtAsc(1L)).thenReturn(List.of(new ChatMessage(1L, "USER", "Hi")));
        assertThat(service.history(1L)).hasSize(1);
    }
}
