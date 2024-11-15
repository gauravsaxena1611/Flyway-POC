-- Add an index to the email column of the customer table to optimize queries
CREATE INDEX idx_customer_email
ON customer (email);
