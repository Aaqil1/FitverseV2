package com.fitverse.shared.ai.dto;

public class ChatCompletion {

    private final String reply;
    private final boolean escalate;

    public ChatCompletion(String reply, boolean escalate) {
        this.reply = reply;
        this.escalate = escalate;
    }

    public String getReply() {
        return reply;
    }

    public boolean shouldEscalate() {
        return escalate;
    }
}
