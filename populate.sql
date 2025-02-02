USE lost_and_found;

-- Populate the lost_items table
INSERT INTO lost_items (
    item_type, item_subtype, item_description, location_details, 
    date_time_lost, item_photo_path, reporter_name, 
    reporter_email, reporter_phone, status
) VALUES
    ('Electronics', 'Handfan', 'White fan with usual fan design, not the cylinder ones.',
     '5th floor, East Wing, Main Building', '2025-01-30 13:00:00', 'img 1.jpg',
     'Wendel de Dios', 'wendeldedios2122@gmail.com', '09324069350', 'PENDING'),
    
    ('Personal Items', 'Wallet', 'Red wallet with CLN logo',
     'Lagoon, Printing Shop Area', '2025-01-30 20:00:00', 'img 2.jpg',
     'Jeremy Diaz', 'jdiaz@gmail.com', NULL, 'PENDING'),
    
    ('Accessories', 'Glasses', 'Plastic black frame with rectangle lenses. Frame is on the thinner side around the lenses.',
     'Oval', '2025-02-01 12:00:00', 'img 3.jpg',
     'John Paul Viado', 'johnpaulviado07@gmail.com', '09516985312', 'PENDING'),
    
    ('Personal Items', 'Water Bottle', 'Hydro flask, purple color same as picture. Slight dent at the bottom.',
     'Lagoon, tables under the amphitheatre', '2025-01-27 00:00:00', 'img 4.jpg',
     'Jermaine Dawson', 'jaydee@gmail.com', NULL, 'PENDING'),
    
    ('Clothing', 'Hat', 'Blue cap with Brooklyn logo. Almost a denim pants design on the actual cap.',
     'Main Gymnasium', '2025-01-13 00:00:00', 'img 5.jpg',
     'John Demetren', 'jdeme@gmail.com', NULL, 'PENDING'),

    ('Electronics', 'Smartphone', 'Samsung Galaxy S23 Ultra, Gold, cracked screen at the top left, wallpaper is a couple at Timezone arcade.',
     'Library', '2025-01-29 18:45:00', 'img 6.jpg',
     'Alex Rivera', 'alexrivera@gmail.com', '09876543210', 'PENDING');

-- Populate the found_items table
INSERT INTO found_items (
    item_type, item_subtype, item_description, location_details, 
    date_time_found, item_photo_path, reporter_name, 
    reporter_email, reporter_phone, status
) VALUES
    ('Accessories', 'Glasses', 'Black plastic frame, rectangle shaped glasses',
     'Near Oval', '2025-02-01 11:00:00', 'img 1.jpg',
     'Wendel de Dios', 'wendeldedios2122@gmail.com', '09324069350', 'PENDING'),
    
    ('Personal Items', 'Wallet', 'Small black wallet, square design with a wrap-around zipper and a strap on one side.',
     'Lagoon', '2025-01-03 20:30:00', 'img 2.jpg',
     'John Doe', 'juandomingo@gmail.com', NULL, 'PENDING'),
    
    ('Electronics', 'Handfan', 'white handfan with the usual fan design, not the popular cylinder ones',
     '5th Floor East Wing', '2025-01-30 15:00:00', 'img 3.jpg',
     'John Paul Viado', 'johnpaulviado07@gmail.com', NULL, 'PENDING'),
    
    ('Personal Items', 'Others', 'LRT beep card. ID starts at 637 and ends at 867. Expires on December of 2027',
     'PUP Sidewalk to Maui and El Pueblo', '2025-01-30 14:00:00', 'img 4.jpg',
     'Jean Doe', 'jendomingo@gmail.com', '09516985312', 'PENDING'),
    
    ('School Supplies', 'Folder', 'Clear, binder-type folder',
     'Lagoon, Stall 14', '2025-02-01 14:30:00', 'img 5.jpg',
     'Jane Doe', 'janelledomingo@gmail.com', NULL, 'REPORTED'),

    ('Electronics', 'Smartphone', 'Gold Samsung Galaxy, has a wallpaper of couple in an arcade, cracked screen at top left',
     'Library, Study Area', '2025-01-30 21:15:00', 'img 6.jpg',
     'Chris Mendoza', 'chrismendoza@gmail.com', '09123456789', 'PENDING');     

SELECT 'Data populated successfully.' AS STATUS;
