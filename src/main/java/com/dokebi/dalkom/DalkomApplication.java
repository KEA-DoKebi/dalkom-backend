package com.dokebi.dalkom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DalkomApplication {

	public static void main(String[] args) {
		SpringApplication.run(DalkomApplication.class, args);
	}

}
