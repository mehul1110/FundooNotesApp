package com.fundoonotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for the FundooNotes application.
 * Bootstraps the Spring Boot application and enables scheduling and caching capabilities.
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class FundoonotesApplication {

	/**
	 * Main entry point of the application.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(FundoonotesApplication.class, args);
	}

}
