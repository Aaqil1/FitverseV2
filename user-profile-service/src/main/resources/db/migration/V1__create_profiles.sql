CREATE TABLE IF NOT EXISTS profiles (
    user_id BIGINT PRIMARY KEY,
    height_cm INT,
    weight_kg INT,
    age INT,
    gender VARCHAR(20),
    goal VARCHAR(30)
);
