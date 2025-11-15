CREATE TABLE user_tracking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    device VARCHAR(255),
    platform VARCHAR(255),
    endpoint VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
