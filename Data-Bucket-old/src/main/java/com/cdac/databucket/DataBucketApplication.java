package com.cdac.databucket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;



@SpringBootApplication
@EnableEmailTools
public class DataBucketApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataBucketApplication.class, args);
		
		
		
	}

}
