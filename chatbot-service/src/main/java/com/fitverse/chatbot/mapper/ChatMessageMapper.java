package com.fitverse.chatbot.mapper;

import com.fitverse.chatbot.dto.ChatMessageResponse;
import com.fitverse.chatbot.entity.ChatMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {

    ChatMessageResponse toResponse(ChatMessage message);
}
