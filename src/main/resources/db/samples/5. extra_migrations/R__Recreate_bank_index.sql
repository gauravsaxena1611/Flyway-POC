-- Recreate or update the index on the 'name' column in the 'bank' table
CREATE OR REPLACE INDEX idx_bank_name
ON bank (name);
