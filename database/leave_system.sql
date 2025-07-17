CREATE DATABASE IF NOT EXISTS leave_system;
USE leave_system;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role ENUM('employee', 'admin') DEFAULT 'employee'
);

CREATE TABLE leave_requests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    start_date DATE,
    end_date DATE,
    reason TEXT,
    status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Sample Data
INSERT INTO users (username, password, role) VALUES ('employee1', 'pass123', 'employee');
INSERT INTO users (username, password, role) VALUES ('admin1', 'admin123', 'admin');
