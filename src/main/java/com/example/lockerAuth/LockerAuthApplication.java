package com.example.lockerAuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class LockerAuthApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(LockerAuthApplication.class, args);
	}

	@Bean
		 WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("https://proyecto-canchas-xi.vercel.app/");
			}
		};
	}
}
