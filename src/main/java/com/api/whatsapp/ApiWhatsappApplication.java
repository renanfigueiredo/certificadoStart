package com.api.whatsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class ApiWhatsappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiWhatsappApplication.class, args);
	}
	
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select() 
		        .apis(RequestHandlerSelectors.any()) 
		        .paths(PathSelectors.any()) 
		        .build();

	}
	


}
