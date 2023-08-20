package com.xinjia.carpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * FinalProjectApplication class is the main entry point for the application.
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class FinalProjectApplication {

  /**
   * Main method for the application.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(FinalProjectApplication.class, args);
  }

}
