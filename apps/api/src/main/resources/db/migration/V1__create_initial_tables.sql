-- V1__create_initial_tables.sql

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    name VARCHAR(255),
    avatar_url TEXT,
    phone VARCHAR(20),
    role ENUM('user', 'host', 'admin') NOT NULL DEFAULT 'user',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE hotels (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name_vi VARCHAR(255) NOT NULL,
    name_en VARCHAR(255) NOT NULL,
    description_vi TEXT,
    description_en TEXT,
    address TEXT,
    city VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    host_id BIGINT,
    rating FLOAT DEFAULT 0,
    review_count INT DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (host_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE hotel_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hotel_id BIGINT NOT NULL,
    url TEXT NOT NULL,
    is_cover BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE
);

CREATE TABLE rooms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hotel_id BIGINT NOT NULL,
    room_type_vi VARCHAR(255),
    room_type_en VARCHAR(255),
    max_guest INT,
    price_per_night DECIMAL(10, 2),
    total_rooms INT,
    available_rooms INT,
    amenities JSON,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE
);

CREATE TABLE bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    hotel_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    total_price DECIMAL(10, 2),
    status ENUM('pending', 'confirmed', 'cancelled', 'completed') NOT NULL DEFAULT 'pending',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE RESTRICT,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE RESTRICT
);

CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    hotel_id BIGINT NOT NULL,
    rating INT,
    comment TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE
);

CREATE TABLE destinations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name_vi VARCHAR(255),
    name_en VARCHAR(255),
    description_vi TEXT,
    description_en TEXT,
    latitude DOUBLE,
    longitude DOUBLE,
    type ENUM('city', 'region', 'attraction'),
    images JSON
);

CREATE TABLE tours (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title_vi VARCHAR(255),
    title_en VARCHAR(255),
    description_vi TEXT,
    description_en TEXT,
    price DECIMAL(10, 2),
    duration_hours INT,
    destination_id BIGINT,
    images JSON,
    FOREIGN KEY (destination_id) REFERENCES destinations(id) ON DELETE CASCADE
);

CREATE TABLE favorites (
    user_id BIGINT NOT NULL,
    hotel_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, hotel_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE
);
