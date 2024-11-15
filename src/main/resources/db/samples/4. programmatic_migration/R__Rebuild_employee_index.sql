-- Refresh or rebuild the index on the 'name' column of the 'employee' table
CREATE OR REPLACE INDEX idx_employee_name
ON employee (name);
