package com.example.bugwarshealerbackend;

import com.example.bugwarshealerbackend.config.CorsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CorsConfig.class)

public class BugwarsHealerBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BugwarsHealerBackendApplication.class, args);
	}

}
