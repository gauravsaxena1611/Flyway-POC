-- V9__Add_stored_procedure.sql
CREATE OR REPLACE PROCEDURE get_customer_by_account(IN account_num VARCHAR(20), OUT customer_name VARCHAR(100))
BEGIN
    SELECT CONCAT(first_name, ' ', last_name)
    INTO customer_name
    FROM customer
    WHERE id = (SELECT customer_id FROM account WHERE account_number = account_num);
END;
