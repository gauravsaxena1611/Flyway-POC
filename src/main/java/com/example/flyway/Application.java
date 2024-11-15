package com.example.flyway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
@Slf4j
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
//		initDatabaseUsingPlainJDBCWithURL();
		//initDatabaseUsingPlainJDBCWithFile();
		//initDatabaseUsingSpring(ctx.getBean(DataSource.class));
	}

	/**
	 * Initialize in-memory database using plain JDBC and SQL statements in the URL.
	 */
	private static void initDatabaseUsingPlainJDBCWithURL() {
		try (Connection conn = DriverManager.
				getConnection("jdbc:h2:mem:testdbserver;DB_CLOSE_DELAY=-1;INIT=CREATE SCHEMA IF NOT EXISTS citi;SET SCHEMA citi",
						"sa",
						"")) {
			//conn.createStatement().execute("create table users (name VARCHAR(100) NOT NULL, email VARCHAR(100) NOT NULL);");
			//System.out.println("Created table users");
			//conn.createStatement().execute("insert into users (name, email) values ('Mike', 'mike@citi.com')");
			//System.out.println("Added user mike");
		} catch (Exception e) {
            log.error("Application > initDatabaseUsingPlainJDBCWithURL > {}", e.getMessage());
		}
	}

	/**
	 * Initialize in-memory database using plain JDBC and SQL statements in a file.
	 */
	private static void initDatabaseUsingPlainJDBCWithFile() {
		try (Connection conn = DriverManager.
				getConnection("jdbc:h2:mem:testdbserver;INIT=RUNSCRIPT FROM 'src/main/resources/h2init.sql';",
						"sa",
						"")) {
			conn.createStatement().execute("insert into users (name, email) values ('Mike', 'mike@citi.com')");
			System.out.println("Added user mike");
		}
		catch (Exception e) {
            log.error("Application > initDatabaseUsingPlainJDBCWithFile > {}", e.getMessage());
		}
	}

	/**
	 * Initialize in-memory database using Spring Boot properties.
	 * See article for full details of required properties for this method to work.
	 */
	private static void initDatabaseUsingSpring(DataSource ds) {
		try (Connection conn = ds.getConnection()) {
			conn.createStatement().execute("insert into users (name, email) values ('Mike', 'mike@citi.com')");
			System.out.println("Added user mike");
		}
		catch (Exception e) {
            log.error("Application > initDatabaseUsingSpring > {}", e.getMessage());
		}
	}

}
