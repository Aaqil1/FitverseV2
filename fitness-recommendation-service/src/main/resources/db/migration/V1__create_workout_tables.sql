CREATE TABLE IF NOT EXISTS workout_plan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    plan_json LONGTEXT NOT NULL,
    starts_on DATE,
    ends_on DATE
);

CREATE TABLE IF NOT EXISTS workout_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    exercise VARCHAR(255) NOT NULL,
    duration_min INT NOT NULL,
    calories_burned INT NOT NULL,
    logged_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
