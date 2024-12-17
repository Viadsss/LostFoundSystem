USE lost_and_found;

-- Populate the lost_items table
INSERT INTO lost_items (item_type, item_subtype, item_description, location_details, date_time_lost, item_photo_path, reporter_name, reporter_email, reporter_phone, status)
VALUES
('Electronics', 'Smartphone', 'Black iPhone 13 with a cracked screen', 'Near the park bench in Central Park', '2024-11-10 14:00:00', '1.jpg', 'John Doe', 'john.doe@example.com', '12345678901', 'PENDING'),
('Accessories', 'Wallet', 'Brown leather wallet with credit cards and IDs', 'Metro Station - Platform 2', '2024-12-01 08:30:00', '2.jpg', 'Jane Smith', 'jane.smith@example.com', '09876543211', 'PENDING'),
('Clothing', 'Jacket', 'Black North Face jacket, size L', 'Coffee shop near Elm Street', '2024-12-05 18:00:00', '3.jpg', 'Alice Johnson', 'alice.johnson@example.com', NULL, 'PENDING');

-- Populate the found_items table
INSERT INTO found_items (item_type, item_subtype, item_description, location_details, date_time_found, item_photo_path, reporter_name, reporter_email, reporter_phone, status)
VALUES
('Electronics', 'Smartphone', 'Black iPhone 13 with a cracked screen', 'Lost and Found desk, Central Park', '2024-11-11 10:00:00', '4.jpg', 'Central Park Staff', 'staff@centralpark.com', '11223344551', 'REPORTED'),
('Accessories', 'Wallet', 'Brown leather wallet with some cash, no cards', 'Metro Station - Lost Items Office', '2024-12-02 09:00:00', '5.jpg', 'Metro Staff', 'lostandfound@metro.com', '22334455661', 'REPORTED'),
('Clothing', 'Scarf', 'Red wool scarf, brand new', 'Bench near Elm Street Coffee Shop', '2024-12-06 19:00:00', '6.jpg', 'Emma Brown', 'emma.brown@example.com', NULL, 'REPORTED');

-- Populate the match_items table
INSERT INTO match_items (lost_item_id, found_item_id, id_photo_path, profile_path, status)
VALUES
(1, 1, NULL, NULL, 'MATCHED'),
(2, 2, '7.jpg', '8.jpg', 'RESOLVED');

SELECT 'Data populated successfully.' AS STATUS;
