package com.fitverse.shared.ai;

import com.fitverse.shared.ai.dto.ChatCompletion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "fitverse.ai.chat", name = "provider", havingValue = "openai")
public class OpenAIAdapter implements ChatAdapter {

    private static final Logger log = LoggerFactory.getLogger(OpenAIAdapter.class);

    private final String model;

    public OpenAIAdapter(@Value("${fitverse.ai.chat.model:gpt-4o}") String model) {
        this.model = model;
    }

    @Override
    public ChatCompletion chat(String conversationId, String message) {
        log.info("Calling OpenAI model {} for conversation {}", model, conversationId);
        // Placeholder response until actual OpenAI integration is wired
        return new ChatCompletion("[OpenAI] " + message, false);
    }
}
