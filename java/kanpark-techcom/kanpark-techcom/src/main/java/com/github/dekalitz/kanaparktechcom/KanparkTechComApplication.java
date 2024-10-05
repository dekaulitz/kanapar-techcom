package com.github.dekalitz.kanaparktechcom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class KanparkTechComApplication {

	public static void main(String[] args) {
		// Force UTC as the default timezone
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(KanparkTechComApplication.class, args);
	}

}
