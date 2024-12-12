CREATE DATABASE IF NOT EXISTS lost_and_found;

USE lost_and_found;

-- Table for Lost Items
CREATE TABLE IF NOT EXISTS lost_items (
    lost_item_id INT AUTO_INCREMENT PRIMARY KEY,
    item_type VARCHAR(255) NOT NULL,
    item_subtype VARCHAR(255) NOT NULL,
    item_description TEXT NOT NULL,
    location_details TEXT NOT NULL COMMENT 'Approximate or estimated location details',
    date_time_lost DATETIME NOT NULL COMMENT 'Approximate or estimated date and time lost',
    item_photo_path VARCHAR(255) DEFAULT NULL,
    reporter_name VARCHAR(255) NOT NULL,
    reporter_email VARCHAR(255) NOT NULL,
    reporter_phone VARCHAR(20) DEFAULT NULL,
    status ENUM('pending', 'matched', 'returned') NOT NULL DEFAULT 'pending',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for Found Items
CREATE TABLE IF NOT EXISTS found_items (
    found_item_id INT AUTO_INCREMENT PRIMARY KEY,
    item_type VARCHAR(255) NOT NULL,
    item_subtype VARCHAR(255) NOT NULL,
    item_description TEXT NOT NULL,
    location_details TEXT NOT NULL COMMENT 'Approximate or estimated location details',
    date_time_found DATETIME NOT NULL COMMENT 'Approximate or estimated date and time found',
    item_photo_path VARCHAR(255) DEFAULT NULL,
    reporter_name VARCHAR(255) NOT NULL,
    reporter_email VARCHAR(255) NOT NULL,
    reporter_phone VARCHAR(20) DEFAULT NULL,
    status ENUM('reported', 'pending', 'matched', 'returned') NOT NULL DEFAULT 'reported',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for Lost and Found Matches
CREATE TABLE IF NOT EXISTS lost_found_match (
    match_id INT AUTO_INCREMENT PRIMARY KEY,
    lost_item_id INT NOT NULL,
    found_item_id INT NOT NULL,
    id_photo_path VARCHAR(255) DEFAULT NULL COMMENT 'Photo of ID provided by claimant',
    profile_path VARCHAR(255) DEFAULT NULL COMMENT 'Onsite photo of claimant during retrieval',
    status ENUM('matched', 'resolved') NOT NULL DEFAULT 'matched',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (lost_item_id) REFERENCES lost_items(lost_item_id) ON DELETE CASCADE,
    FOREIGN KEY (found_item_id) REFERENCES found_items(found_item_id) ON DELETE CASCADE
);

SELECT 'Database and Tables initialized successfully.' AS STATUS;
