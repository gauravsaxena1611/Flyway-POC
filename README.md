# Flyway Sample Project for Banking Application

## Overview

Flyway is a tool that helps manage database migrations in any project. 

A `migration` refers to the process of changing the database schema over time (e.g., creating tables, modifying columns, etc.).

Flyway allows to write migration scripts `(SQL scripts)` that Flyway will run automatically when the application starts, ensuring that the database schema is always in the right state.

This project demonstrates how to use Flyway in a Spring Boot application with H2 as the database. The project is structured to gradually introduce various Flyway concepts, starting from simple migrations to more advanced database operations like stored procedures and complex transactions.

### Flyway Script Naming Conventions: V__, U__, and R__

Flyway uses a structured naming convention for migration scripts, allowing developers to manage database changes systematically and reliably. Below is an explanation of the significance and purpose of scripts prefixed with V__, U__, and R__.

- **V__ (Versioned Scripts)**
  - **Purpose:** These scripts define versioned migrations that represent specific changes applied sequentially to the database schema or data.
  - **Usage:** Scripts with this prefix, such as V1__Create_customer_table.sql or V2__Insert_initial_data.sql, introduce changes like creating tables, altering schemas, or inserting initial data.
  - **Significance:**
  - **Sequential Execution:** Versioned scripts execute in the order of their version numbers, ensuring incremental updates to the database.
   - **Audit Trail:** Flyway records each version applied in a schema history table, making it easy to track migrations.


- **U__ (Undo Scripts)**
  - **Purpose:** Undo scripts are optional and allow for manual rollback of versioned migrations in case of errors or a need to revert changes.
  - **Usage:** A script like U1__Undo_create_customer_table.sql can be used to reverse the effects of a migration (e.g., dropping a table or removing inserted data).
  - **Significance:**
  - **Controlled Rollback:** Provides a structured way to undo changes without relying on ad hoc solutions.
  - **Testing and Development:** Useful during iterative development or testing phases when frequent schema adjustments are required.


- **R__ (Repeatable Scripts)**
   - **Purpose:** These scripts define repeatable migrations that can be re-applied as needed, making them ideal for maintaining database objects like views, stored procedures, or functions.
   - **Usage:** For example, R__Rebuild_materialized_view.sql can recreate or refresh a materialized view whenever underlying data changes.
   - **Significance:**
   - **Always Up-to-Date:** Repeatable migrations ensure that non-versioned database objects remain current with the latest logic.
   - **No Versioning:** Unlike versioned scripts, they are identified by their checksum, meaning they are re-applied only if their contents change.


### Prerequisites
- Java 8 or above
- Maven
- Spring Boot


## Project Structure

Sample migration scripts are provided to experience how Flyway migration are writen and executed. 

All the sample scripts are available in `sample` folder, under `resources`.

The sample migration scripts are divided into 5 different folders (`basic`, `intermediate`, `advance`, `programmatic migration`, and `extra migrations`) to help users understand Flyway step by step. 

Start with the basic folder and progressively move to more complex scenarios.


### Basic Folder
1. **V1__Create_customer_table.sql**: Creates the `customer` table.
2. **V2__Insert_initial_data.sql**: Inserts initial customer data.
3. **V3__Add_non_null_column_to_customer.sql**: Adds a `phone_number` column to the `customer` table, first nullable, then setting it as `NOT NULL` after back filling.
4. **V4__Add_index_to_customer_email.sql**: Adds an index to the `email` column in the `customer` table.

### Intermediate Folder
1. **V5__Create_account_table.sql**: Creates the `account` table with a foreign key to `customer`.
2. **V6__Insert_account_data.sql**: Inserts initial account data.
3. **V7__Alter_account_table_add_account_type.sql**: Adds an `account_type` column to the `account` table, with a default value and `NOT NULL` constraint.
4. **V8__Add_foreign_key_to_account_table.sql**: Adds a foreign key constraint to the `account` table, linking it to the `customer` table.


### Advance Folder
1. **V9__Add_stored_procedure.sql**: Creates a Stored Procedure called `get_customer_by_account` in the database. This procedure is designed to retrieve a `customer’s` full name based on an account number.
2. **V10__Complex_transaction_procedure.sql**: Creates a Stored Procedure called `transfer_money`, which facilitates transferring funds between two accounts. This procedure takes three input parameters:
- `from_account` (source account number),
- `to_account` (destination account number),
- `amount` (the amount to transfer as a decimal value with two decimal places).
  This procedure ensures that the transfer happens atomically—either both accounts are updated, or an error is raised, keeping the database consistent. It’s particularly useful for preventing partial updates during a transfer.


### Programmatic Folder
1. **V11__Create_employee_table.sql**: Creates the `employee` table.
2. **V12__Insert_employee_data.sql**: Inserts two records into `employee` table.
3. **V13__Add_salary_column_to_employee.sql**: Performs an ALTER TABLE operation to modify the `employee` table by adding a new `salary` column.
4. **R__Rebuild_employee_index.sql**: Ensures the index on the `employee` table's `name` column is updated or recreated if its definition changes.
5. **U__Undo_add_salary_column_to_employee.sql**: Reverts the migration that added the `salary` column to the `employee` table, useful if the change is no longer needed or was applied in error.


### Extra Migration Folder
1. **V1__Create_bank_table.sql**: Creates the `bank` table
2. **V2__Alter_bank_table_add_column.sql**: Modifies existing `bank` table by adding a new column `established_year`.
3. **V3__Add_index_to_bank_table.sql**: Creates an index on `name` column of the `bank` table.
4. **V4__Drop_bank_table.sql**: Deletes the `bank` table from the database
5. **R__Recreate_bank_index.sql**: Ensures that the index on the `name` column of the `bank` table remains up-to-date. This script can be reapplied whenever the `bank` table changes or the index definition needs to be modified.
6. **R__Refresh_bank_materialized_view.sql**: Maintains a materialized view that summarizes the number of branches for each bank. This script can be reapplied as needed whenever there are changes in the underlying `bank` table.
7. **R__Refresh_bank_summary_view.sql**: Provides a reusable summary of the number of branches and the earliest year of establishment for each bank. The script can be reapplied whenever there are structural or data updates to the `bank` table.
8. **U__Undo_add_established_year_column.sql**: Reverts the addition of the `established_year` column, undoing the migration if it is no longer required or was introduced in error.
9. **U__Undo_create_bank_table.sql**: Fully removes the `bank` table, including any dependencies like foreign keys or indexes.
10. **U__Undo_drop_bank_table.sql**: Restores the `bank` table after it was dropped, recreating the structure and reintroducing some sample data for development or testing purposes.


## Callbacks

Callbacks are special `hooks` that can be used with Flyway to run custom code at specific points during the migration process. 

These points could be before or after a migration is applied, allowing to do things like logging, data validation, or even modifying the environment.

Think of callbacks as special `"events"` that occur during the migration process, where we can tell Flyway to trigger specific actions at those moments.

**`DBMigrationCallback.java`** (Java-based callback) - refer callback package in java source code
   - This is a custom Flyway callback used to hook into different points of the database migration process. 
   - It allows us to perform actions before, during, and after migration scripts are executed.
   - **Key Features:**
     - **Before Migration:** Logs the start of each migration and allows pre-migration checks.
     - **After Migration:** Logs when a migration is completed and can trigger actions like notifying external systems.
     - **After Each Migration:** Logs the completion of each individual migration script.
     - **After Validation:** Logs the completion of migration validation to ensure scripts are in the correct order.
     - **After Migration Applied:** Runs cleanup or finalization tasks after all migrations are successfully applied.



## Programmatic DB Migration (Java Package)

This folder contains examples that demonstrate how to automate Flyway database migrations programmatically within a Java Spring Boot application. These examples showcase different ways to configure and trigger Flyway migrations via code, handling scenarios like migration failures.

1. **FlywayMigrationConfig.java**:
   - A Java-based configuration that automatically triggers Flyway migrations during application startup.
   - It demonstrates how to programmatically achieve the same behavior of executing all Flyway migrations before the application starts.


2. **FlywayMigrationController.java**: 
   - Controller to demonstrate Flyway database migrations programmatically. 
   - This REST controller exposes an endpoint that triggers Flyway migrations when called.

2. **FlywayMigrationService.java**:
   - Service class responsible for managing Flyway database migrations programmatically.
   - Programmatically control when migrations are applied and handle migration failures.



## Running the Project

1. Clone the repository:
    ```bash
    git clone https://github.com/your-repo/banking-app.git
    cd banking-app
    ```

2. Build and run the Spring Boot application:
    ```bash
    mvn spring-boot:run
    ```

3. Check the Flyway migration logs in the console or via H2's in-memory database console:
    ```bash
    http://localhost:8080/h2-console
    ```

4. Apply the migrations in sequence by checking the different folders. Start with the `basic` folder, then move to `intermediate`, and finally `advanced`.



### Flyway Concepts Covered
- DDL (Creating tables, altering columns)
- DML (Inserting, updating, and deleting data)
- Complex operations (Stored procedures, transactions)

By following the sequence of migrations, you will learn the core features of Flyway and gain expertise in applying database migrations and using callbacks for advanced database management.


good video to follow - https://www.youtube.com/watch?v=dJDBP7pPA-o
