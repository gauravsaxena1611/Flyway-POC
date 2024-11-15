-- Recreate the 'bank' table and restore its data
CREATE TABLE bank (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    address VARCHAR(255),
    established_year INT
);

-- Insert sample data to restore the state
INSERT INTO bank (id, name, address, established_year) VALUES
(1, 'Bank of XYZ', '123 Main St', 1985),
(2, 'ABC Bank', '456 Market Ave', 1990);
