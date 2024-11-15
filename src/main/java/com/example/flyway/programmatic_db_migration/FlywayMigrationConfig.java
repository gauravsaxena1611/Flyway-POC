package com.example.flyway.programmatic_db_migration;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  This code is commented out to prevent it from executing during application startup.
 *
 *  This is a Java-based configuration that triggers Flyway migrations automatically upon application startup.
 *  It demonstrates how to programmatically achieve the same behavior of executing all Flyway migrations before the application starts.
 *
 */

//@Configuration
public class FlywayMigrationConfig {

    //@Bean
    public Flyway flyway() {
        // Default Flyway configuration
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:h2:tcp://localhost/./src/main/resources/db/flyway-poc-db", "sa", "")
                .schemas("default-schema") // Default schema for initial migrations
                .locations("classpath:db/programmatic_migration") // Default migration location
                .load();

        // Run default migrations on application startup
        flyway.migrate();

        return flyway;
    }
}
