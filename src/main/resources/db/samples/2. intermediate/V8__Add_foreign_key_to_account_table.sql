-- Add a foreign key constraint between account and customer tables
ALTER TABLE account
ADD CONSTRAINT fk_customer_account
FOREIGN KEY (customer_id) REFERENCES customer(id)
ON DELETE CASCADE;  -- Delete accounts if the customer is deleted
