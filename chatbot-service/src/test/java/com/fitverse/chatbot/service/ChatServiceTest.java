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
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private ChatMessageRepository repository;

    private final ChatMessageMapper mapper = Mappers.getMapper(ChatMessageMapper.class);

    private ChatService service;

    @BeforeEach
    void setUp() {
        service = new ChatService(repository, mapper);
    }

    @Test
    void sendUserMessagePersistsBotReply() {
        when(repository.save(any(ChatMessage.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ChatMessageRequest request = new ChatMessageRequest();
        request.setUserId(1L);
        request.setMessage("Hello");

        assertThat(service.sendUserMessage(request).getContent()).contains("Echo");
        verify(repository, times(2)).save(any(ChatMessage.class));
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
