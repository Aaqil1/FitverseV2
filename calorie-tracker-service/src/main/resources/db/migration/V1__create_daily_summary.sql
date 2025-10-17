CREATE TABLE IF NOT EXISTS daily_summary (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    date DATE NOT NULL,
    total_calories INT NOT NULL
);
