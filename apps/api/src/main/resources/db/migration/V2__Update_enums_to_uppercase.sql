-- V2__Update_enums_to_uppercase.sql

-- Update users.role
-- Step 1: Change to VARCHAR to allow arbitrary strings
ALTER TABLE users MODIFY COLUMN role VARCHAR(50) NOT NULL DEFAULT 'user';
-- Step 2: Update existing data to uppercase
UPDATE users SET role = UPPER(role);
-- Step 3: Convert back to ENUM with uppercase values
ALTER TABLE users MODIFY COLUMN role ENUM('USER', 'HOST', 'ADMIN') NOT NULL DEFAULT 'USER';

-- Update destinations.type
-- Step 1: Change to VARCHAR
ALTER TABLE destinations MODIFY COLUMN type VARCHAR(50);
-- Step 2: Update existing data
UPDATE destinations SET type = UPPER(type);
-- Step 3: Convert back to ENUM
ALTER TABLE destinations MODIFY COLUMN type ENUM('REGION', 'CITY', 'ATTRACTION', 'SPOT');

-- Update bookings.status
-- Step 1: Change to VARCHAR
ALTER TABLE bookings MODIFY COLUMN status VARCHAR(50) NOT NULL DEFAULT 'pending';
-- Step 2: Update existing data
UPDATE bookings SET status = UPPER(status);
-- Step 3: Convert back to ENUM
ALTER TABLE bookings MODIFY COLUMN status ENUM('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED') NOT NULL DEFAULT 'PENDING';
