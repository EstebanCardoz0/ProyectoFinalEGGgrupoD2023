package com.QuinchApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.QuinchApp.Entidades")
public class QuinchAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuinchAppApplication.class, args);
	}

}
