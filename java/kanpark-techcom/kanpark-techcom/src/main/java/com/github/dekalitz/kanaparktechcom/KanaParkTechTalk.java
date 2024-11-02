package com.github.dekalitz.kanaparktechcom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication// Ensure this package contains the controller
public class KanaParkTechTalk {
	public static void main(String[] args) {
		// Force UTC as the default timezone
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(KanaParkTechTalk.class, args);
	}

}
