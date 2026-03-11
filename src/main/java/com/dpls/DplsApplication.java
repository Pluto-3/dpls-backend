package com.dpls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
@EnableScheduling
public class DplsApplication {
	public static void main(String[] args) {
		SpringApplication.run(DplsApplication.class, args);
	}
}