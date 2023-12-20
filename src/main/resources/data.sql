-- Drop the table if it already exists
DROP TABLE IF EXISTS Users;

-- Create the Users table
CREATE TABLE Users (
    id INT PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255),
);

-- Insert test data
INSERT INTO Users (id, username, email) VALUES
(1, 'Laura', 'user1@example.com'),
(2, 'Ashley', 'user2@example.com'),
(3, 'Rob', 'user3@example.com'),
(4, 'Sam', 'user4@example.com'),
(5, 'Viv', 'user5@example.com');
(6, 'Yagmur', 'user5@example.com');
(7, 'Kimlyn', 'user5@example.com');