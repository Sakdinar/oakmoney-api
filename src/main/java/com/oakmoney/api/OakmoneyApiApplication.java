package com.oakmoney.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.oakmoney.api.config.property.OakmoneyApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(OakmoneyApiProperty.class)
public class OakmoneyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OakmoneyApiApplication.class, args);
	}
}
