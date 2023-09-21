package com.ngts.scm;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ngts.common.*","com.ngts.scm.*"})
public class SchoolManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SchoolManagementSystemApplication.class);
		application.run(args);
	}

}
