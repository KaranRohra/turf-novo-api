package com.turfnovo;

import org.springframework.boot.SpringApplication;

public class TestTurfNovoApplication {

	public static void main(String[] args) {
		SpringApplication.from(TurfNovoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
