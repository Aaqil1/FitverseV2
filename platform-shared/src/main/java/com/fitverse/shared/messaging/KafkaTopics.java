package com.fitverse.shared.messaging;

public final class KafkaTopics {

    public static final String FOOD_LOG_CREATED = "foodlog.created";
    public static final String PLAN_GENERATED = "plan.generated";
    public static final String NOTIFICATION_SEND = "notification.send";

    private KafkaTopics() {
    }
}
