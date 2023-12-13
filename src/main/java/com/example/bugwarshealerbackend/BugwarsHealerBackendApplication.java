package com.example.bugwarshealerbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class}) //This is only here temporarily until we get a database hooked up
public class BugwarsHealerBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BugwarsHealerBackendApplication.class, args);
	}

}
