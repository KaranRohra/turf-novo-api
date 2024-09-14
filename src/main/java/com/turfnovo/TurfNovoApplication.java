package com.turfnovo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAutoConfiguration
@RestController
public class TurfNovoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurfNovoApplication.class, args);
	}

	@Bean
	ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@GetMapping(ApiPathConstants.HELLO_WORLD)
	public String helloWorld() {
		return "Hello, World!";
	}
}
