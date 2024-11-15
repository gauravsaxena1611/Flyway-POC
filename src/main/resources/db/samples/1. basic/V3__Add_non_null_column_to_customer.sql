-- Step 1: Add nullable phone_number column to customer table
ALTER TABLE customer
ADD phone_number VARCHAR(20);

-- Step 2: Backfill phone_number with default data for existing rows
UPDATE customer
SET phone_number = 'Unknown'
WHERE phone_number IS NULL;

-- Step 3: Set the phone_number column to NOT NULL
ALTER TABLE customer
ALTER COLUMN phone_number SET NOT NULL;
