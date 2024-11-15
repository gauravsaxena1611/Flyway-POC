-- V10__Complex_transaction_procedure.sql
CREATE OR REPLACE PROCEDURE transfer_money(IN from_account VARCHAR(20), IN to_account VARCHAR(20), IN amount DECIMAL(15, 2))
BEGIN
    DECLARE from_balance DECIMAL(15, 2);
    DECLARE to_balance DECIMAL(15, 2);

    -- Check balances
    SELECT balance INTO from_balance FROM account WHERE account_number = from_account;
    SELECT balance INTO to_balance FROM account WHERE account_number = to_account;

    IF from_balance >= amount THEN
        -- Deduct from source account
        UPDATE account
        SET balance = balance - amount
        WHERE account_number = from_account;

        -- Add to destination account
        UPDATE account
        SET balance = balance + amount
        WHERE account_number = to_account;
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Insufficient funds in source account';
    END IF;
END;
