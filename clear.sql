USE lost_and_found;

-- Disable foreign key checks
SET foreign_key_checks = 0;

-- Clear the match_items table
TRUNCATE TABLE match_items;

-- Clear the lost_items table
TRUNCATE TABLE lost_items;

-- Clear the found_items table
TRUNCATE TABLE found_items;

-- Enable foreign key checks
SET foreign_key_checks = 1;

SELECT 'All table data cleared successfully.' AS STATUS;
