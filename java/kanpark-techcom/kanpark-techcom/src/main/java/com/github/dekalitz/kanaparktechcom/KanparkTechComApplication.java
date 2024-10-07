package com.github.dekalitz.kanaparktechcom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import java.util.TimeZone;

@SpringBootApplication// Ensure this package contains the controller
@ComponentScan(basePackages = {"com.github.dekalitz.kanaparktechcom"})
public class KanparkTechComApplication {

	public static void main(String[] args) {
		// Force UTC as the default timezone
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(KanparkTechComApplication.class, args);
	}

}
