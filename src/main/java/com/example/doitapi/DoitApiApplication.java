package com.example.doitapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@SpringBootApplication
public class DoitApiApplication {
	public static final Logger logger = LoggerFactory.getLogger(DoitApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DoitApiApplication.class, args);
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//
//				registry.addMapping("/api/v1/hello").allowedOrigins("http://localhost:3000")
//						.allowedMethods("GET", "POST","PUT", "DELETE")
//						.allowedHeaders("Content-Type","Authorization")
//						.allowCredentials(true);
//
//				registry.addMapping("/api/v1/auth/**")
//						.allowedOrigins("http://localhost:3000")
//						.allowedHeaders("Content-Type","Authorization")
//						.allowedMethods("GET", "POST","PUT", "DELETE");
//
//			}
//		};
//	}

}
