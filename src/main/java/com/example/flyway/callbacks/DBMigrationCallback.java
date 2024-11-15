package com.example.flyway.callbacks;

import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Event;
import org.flywaydb.core.api.callback.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 *
 * Why Use Callbacks?
 * Customization: You can customize what happens before, during, and after migrations.
 * Error handling: You can trigger special error-handling behavior if needed.
 * Auditing: You can log migration details for auditing purposes.
 * Data Validation: Ensure data integrity and validation before and after migrations.
 *
 */

public class DBMigrationCallback implements Callback {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBMigrationCallback.class);

    @Override
    public boolean supports(Event event, Context context) {
        // Hooks: This callback supports multiple events related to migrations
        return event == Event.BEFORE_MIGRATE ||
                event == Event.AFTER_MIGRATE ||
                event == Event.AFTER_EACH_MIGRATE ||
                event == Event.AFTER_VALIDATE ||
                event == Event.AFTER_MIGRATE_APPLIED;
    }

    @Override
    public void handle(Event event, Context context) {
        switch (event) {
            case BEFORE_MIGRATE:
                handleBeforeMigrate(context);
                break;
            case AFTER_MIGRATE:
                handleAfterMigrate(context);
                break;
            case AFTER_EACH_MIGRATE:
                handleAfterEachMigrate(context);
                break;
            case AFTER_VALIDATE:
                handleAfterValidate(context);
                break;
            case AFTER_MIGRATE_APPLIED:
                try {
                    handleAfterCallback(context);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                break;
        }
    }

    private void handleBeforeMigrate(Context context) {
        LOGGER.info("Migration starting for script: {}", context.getMigrationInfo().getScript());
        // Before running migrations, you could check if the employee table already exists

        // Additional logic can be added here if needed

        // You can use context.getConnection() to execute a check if necessary
        LOGGER.info("Checking if employee table exists before migration...");
    }

    private void handleAfterMigrate(Context context) {
        LOGGER.info("Migration completed for schema: {}", context.getStatement());
        callExternalSystem(); // Example: Call an external system after migration
    }

    private void handleAfterEachMigrate(Context context) {
        LOGGER.info("Completed migration for script: {}", context.getMigrationInfo().getScript());
        // Log the script that just ran
        // Additional logic can be added here if needed
    }

    private void handleAfterValidate(Context context) {
        LOGGER.info("Validation completed for schema: {}", context.getStatement());
        // Example: Notify the user that the validation was successful
    }

    private void handleAfterCallback(Context context) throws SQLException {
        LOGGER.info("Callback finished for connection: {}", context.getConnection().getSchema());
        // Example: Log information or notify systems about the callback execution
    }

    private void callExternalSystem() {
        // Example: HTTP call to an external service or API
        LOGGER.info("Notifying external system...");
        // You can implement your HTTP call or notification logic here
        LOGGER.info("External system notified successfully.");
    }

    @Override
    public String getCallbackName() {
        return "DBMigrationCallback";
    }

    @Override
    public boolean canHandleInTransaction(Event event, Context context) {
        // Decide if this callback should run within a transaction
        return true; // Allowing transaction handling; modify as needed
    }
}
