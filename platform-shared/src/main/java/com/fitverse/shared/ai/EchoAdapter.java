package com.fitverse.shared.ai;

import com.fitverse.shared.ai.dto.ChatCompletion;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EchoAdapter implements ChatAdapter {

    @Override
    public ChatCompletion chat(String conversationId, String message) {
        boolean escalate = message.toLowerCase().contains("coach");
        return new ChatCompletion("You said: " + message, escalate);
    }
}
