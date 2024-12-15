-- Clear the lost_items table
DELETE FROM lost_items;

-- Clear the found_items table
DELETE FROM found_items;

-- Clear the match_items table
DELETE FROM match_items;

SELECT 'All table data cleared successfully.' AS STATUS;
