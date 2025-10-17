package com.fitverse.shared.ai;

import com.fitverse.shared.ai.dto.ChatCompletion;

public interface ChatAdapter {

    ChatCompletion chat(String conversationId, String message);
}
