-- Step 1: Add nullable account_type column to the account table
ALTER TABLE account
ADD account_type VARCHAR(20);

-- Step 2: Backfill the account_type with a default value for existing records
UPDATE account
SET account_type = 'Savings'
WHERE account_type IS NULL;

-- Step 3: Set the account_type column to NOT NULL to ensure data integrity
ALTER TABLE account
ALTER COLUMN account_type SET NOT NULL;
