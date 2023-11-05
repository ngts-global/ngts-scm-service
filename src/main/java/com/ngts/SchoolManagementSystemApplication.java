package com.ngts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ngts.common.*","com.ngts.scm.*","com.ngts.common.*", "com.ngts.auth.*"})
public class SchoolManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SchoolManagementSystemApplication.class);
		application.run(args);
	}

}
