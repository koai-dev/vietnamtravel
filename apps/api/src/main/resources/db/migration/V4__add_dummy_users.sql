-- V4__add_dummy_users.sql

-- Password for all users is "password" (hashed with bcrypt)
-- A real one for "password" is $2a$10$eACC4I2gR4U1u20p7jVnIuF.0e.dSSb6DU2a.rLi4A9y.82m.cK8W

INSERT INTO users (email, password_hash, name, phone, role) VALUES
('admin@example.com', '$2a$10$eACC4I2gR4U1u20p7jVnIuF.0e.dSSb6DU2a.rLi4A9y.82m.cK8W', 'Admin User', '1234567890', 'admin'),
('host1@example.com', '$2a$10$eACC4I2gR4U1u20p7jVnIuF.0e.dSSb6DU2a.rLi4A9y.82m.cK8W', 'Host One', '1112223333', 'host'),
('user1@example.com', '$2a$10$eACC4I2gR4U1u20p7jVnIuF.0e.dSSb6DU2a.rLi4A9y.82m.cK8W', 'Alice Smith', '0987654321', 'user'),
('user2@example.com', '$2a$10$eACC4I2gR4U1u20p7jVnIuF.0e.dSSb6DU2a.rLi4A9y.82m.cK8W', 'Bob Johnson', '0123456789', 'user'),
('user3@example.com', '$2a$10$eACC4I2gR4U1u20p7jVnIuF.0e.dSSb6DU2a.rLi4A9y.82m.cK8W', 'Charlie Brown', '9876543210', 'user'),
('longuserwithaverylongname@example.com', '$2a$10$eACC4I2gR4U1u20p7jVnIuF.0e.dSSb6DU2a.rLi4A9y.82m.cK8W', 'John Jacob Jingleheimer Schmidt', '1231231234', 'user'),
('anotherhost@example.com', '$2a$10$eACC4I2gR4U1u20p7jVnIuF.0e.dSSb6DU2a.rLi4A9y.82m.cK8W', 'Jane Doe', '4445556666', 'host');
