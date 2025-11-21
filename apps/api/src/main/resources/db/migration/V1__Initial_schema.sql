-- V1__Initial_schema.sql

-- Users Table
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

-- Destinations Table
CREATE TABLE destinations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name_vi VARCHAR(255),
    name_en VARCHAR(255),
    description_vi TEXT,
    description_en TEXT,
    latitude DOUBLE,
    longitude DOUBLE,
    type ENUM('region', 'city', 'attraction', 'spot'),
    images TEXT, -- JSON list
    parent_id BIGINT,
    slug VARCHAR(255) UNIQUE,
    address VARCHAR(512),
    city VARCHAR(255),
    tags TEXT,
    best_time_to_visit VARCHAR(255),
    opening_hours VARCHAR(255),
    price_from DECIMAL(10, 2),
    price_to DECIMAL(10, 2),
    external_links TEXT,
    address_link VARCHAR(1024),
    avg_rating DOUBLE DEFAULT 0.0,
    review_count INT DEFAULT 0,
    views_count BIGINT DEFAULT 0,
    favorites_count INT DEFAULT 0,
    status ENUM('ACTIVE', 'INACTIVE', 'DRAFT') DEFAULT 'ACTIVE',
    sort_order INT DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES destinations(id) ON DELETE CASCADE
);

-- Hotels Table
CREATE TABLE hotels (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name_vi VARCHAR(255) NOT NULL,
    name_en VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE,
    description_vi TEXT,
    description_en TEXT,
    address TEXT,
    city VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    address_link VARCHAR(1024),
    contact TEXT, -- JSON object
    images TEXT, -- JSON list
    min_price DECIMAL(10, 2),
    max_price DECIMAL(10, 2),
    amenities TEXT, -- JSON list
    check_in_time VARCHAR(50),
    check_out_time VARCHAR(50),
    cancellation_policy TEXT,
    child_policy TEXT,
    pet_policy TEXT,
    tags TEXT, -- JSON list
    external_booking_links TEXT, -- JSON list
    rating FLOAT DEFAULT 0,
    review_count INT DEFAULT 0,
    views_count BIGINT DEFAULT 0,
    favorites_count INT DEFAULT 0,
    host_id BIGINT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (host_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Rooms Table
CREATE TABLE rooms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hotel_id BIGINT NOT NULL,
    room_type_vi VARCHAR(255),
    room_type_en VARCHAR(255),
    max_guest INT,
    price_per_night DECIMAL(10, 2),
    total_rooms INT,
    available_rooms INT,
    amenities TEXT, -- JSON
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE
);

-- Bookings Table
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

-- Favorites Table
CREATE TABLE favorites (
    user_id BIGINT NOT NULL,
    hotel_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, hotel_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE
);

-- Hotel Images Table
CREATE TABLE hotel_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hotel_id BIGINT NOT NULL,
    url TEXT NOT NULL,
    is_cover BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE
);

-- Local Foods Table
CREATE TABLE local_foods (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    destination_id BIGINT NOT NULL,
    name_vi VARCHAR(255) NOT NULL,
    name_en VARCHAR(255),
    description_vi TEXT,
    description_en TEXT,
    images TEXT, -- JSON list
    FOREIGN KEY (destination_id) REFERENCES destinations(id) ON DELETE CASCADE
);

-- Notifications Table
CREATE TABLE notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    image TEXT,
    type ENUM('SYSTEM', 'PROMOTION', 'BOOKING', 'WARNING', 'UPDATE') NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    metadata TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Restaurants Table
CREATE TABLE restaurants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    images TEXT NOT NULL,
    address VARCHAR(255) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    destination_id BIGINT NOT NULL,
    FOREIGN KEY (destination_id) REFERENCES destinations(id) ON DELETE CASCADE
);

-- Restaurant Local Foods Table
CREATE TABLE restaurant_local_foods (
    restaurant_id BIGINT NOT NULL,
    local_food_id BIGINT NOT NULL,
    PRIMARY KEY (restaurant_id, local_food_id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE,
    FOREIGN KEY (local_food_id) REFERENCES local_foods(id) ON DELETE CASCADE
);

-- Reviews Table
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

-- Tours Table
CREATE TABLE tours (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title_vi VARCHAR(255),
    title_en VARCHAR(255),
    description_vi TEXT,
    description_en TEXT,
    price DECIMAL(10, 2),
    duration_hours INT,
    destination_id BIGINT,
    images TEXT, -- JSON
    FOREIGN KEY (destination_id) REFERENCES destinations(id) ON DELETE CASCADE
);

-- User Tracking Table
CREATE TABLE user_tracking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    device VARCHAR(255),
    platform VARCHAR(255),
    endpoint VARCHAR(255) NOT NULL,
    os VARCHAR(255),
    os_version VARCHAR(255),
    ip_address VARCHAR(255),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
