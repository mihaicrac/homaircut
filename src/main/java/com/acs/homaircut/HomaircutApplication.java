package com.acs.homaircut;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomaircutApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomaircutApplication.class, args);
	}

	//Creating bean keycloakConfigResolver
	//The use of this keycloakConfigResolver reference will make the application to use Spring Boot configuration
	//properties file instead of the Keycloak default keycloak.json file
	@Bean
	public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
		return new KeycloakSpringBootConfigResolver();
	}

}
