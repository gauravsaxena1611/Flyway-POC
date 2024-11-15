package com.example.flyway.programmatic_db_migration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to demonstrate Flyway database migrations programmatically.
 *
 * This REST controller exposes an endpoint that triggers Flyway migrations when called.
 * It leverages the `FlywayMigrationService` to execute database migrations.
 *
 * The migration process can be triggered by accessing the following URL in the browser:
 * <a href="http://localhost:8080/migrate-db/run-migration">http://localhost:8080/migrate-db/run-migration</a>
 *
 * This is useful for triggering migrations manually during application runtime or for testing purposes.
 */

@RestController
@RequestMapping("/migrate-db")
public class FlywayMigrationController {

    private final FlywayMigrationService flywayMigrationService;

    public FlywayMigrationController(FlywayMigrationService flywayMigrationService) {
        this.flywayMigrationService = flywayMigrationService;
    }


    /**
     * Endpoint to trigger Flyway database migrations programmatically.
     *
     * When called, this method will invoke the migration logic provided by the `FlywayMigrationService`.
     * The process can be accessed by navigating to the following URL:
     * <a href="http://localhost:8080/migrate-db/run-migration">http://localhost:8080/migrate-db/run-migration</a>
     *
     * @return a message indicating the status of the migration process
     */
    @GetMapping("/run-migration")
    public String runMigrations() {
        return flywayMigrationService.migrateDatabase();
    }
}

