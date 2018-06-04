package com.redbean.springresearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class SpringResearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringResearchApplication.class, args);
	}
}
