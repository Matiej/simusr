package com.ematiej.simusr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SimusrApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimusrApplication.class, args);
	}

}
