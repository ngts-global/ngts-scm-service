package com.ngts.scm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.ngts.common.*","com.ngts.scm.*"})
public class SchoolManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SchoolManagementSystemApplication.class);
		application.run(args);
	}

}
