package com.dhis2.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Dhis2CacheApplication {

	public static void main(String... args) {
		SpringApplication.run(Dhis2CacheApplication.class, args);
	}

}
