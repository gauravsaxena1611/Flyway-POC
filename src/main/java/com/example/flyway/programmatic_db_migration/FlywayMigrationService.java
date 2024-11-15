package com.example.flyway.programmatic_db_migration;

import com.example.flyway.callbacks.DBMigrationCallback;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;


/**
 * Service class responsible for managing Flyway database migrations programmatically.
 *
 * This service encapsulates the logic for configuring and running Flyway migrations, including specifying
 * the database connection, schema, migration locations, and callback functions. It ensures that migrations
 * are applied successfully to the specified database schema and handles any exceptions that occur during
 * the migration process.
 *
 * The `migrateDatabase` method will trigger the migration process, including repairs to the Flyway schema history,
 * and it returns a status message indicating the result of the operation.
 */

@Service
public class FlywayMigrationService {

    /**
     * Triggers the Flyway migration process for the specified schema.
     *
     * This method creates a new Flyway instance configured to use the provided data source, schema, and migration scripts.
     * It first attempts to repair the Flyway schema history, ensuring that any missing or outdated migration versions
     * are addressed. Then, it applies the migrations to the specified database schema.
     *
     * @return a message indicating the success or failure of the migration process
     */

    public String migrateDatabase() {

        // Create a new Flyway instance for the "java-code-schema"
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:h2:tcp://localhost/./src/main/resources/db/flyway-poc-db", "sa", "")
                .schemas("programmatic-schema") // Specify the schema for controller-specific migrations
                .locations("classpath:db/programmatic_migration")
                .callbacks(new DBMigrationCallback())
                .load();

        // Run migrations for the specified schema
        try {
            flyway.repair(); // Refresh the Flyway schema history to include any new scripts
            flyway.migrate(); // Run the migrations
            System.out.println("FlywayMigrationService > migrateDatabase > Flyway Migrations Successfully RAN !!!");
            return "Flyway Migrations Successfully RAN !!!";
        } catch (Exception e) {
            System.err.println("FlywayMigrationService > migrateDatabase > Migration failed: " + e.getMessage());
            return handleMigrationFailure(e);
        }
    }


    /**
     * Handles migration failure by logging the error and returning a message.
     *
     * This method is called if an exception is thrown during the migration process. It logs the error message
     * and returns a user-friendly message indicating the failure.
     *
     * @param e the exception that occurred during the migration
     * @return a message indicating the migration failure
     */

    private String handleMigrationFailure(Exception e) {
        System.err.println("FlywayMigrationService >  handleMigrationFailure > Handling migration failure...");
        return "Error running Flyway migrations: " + e.getMessage();
    }
}
