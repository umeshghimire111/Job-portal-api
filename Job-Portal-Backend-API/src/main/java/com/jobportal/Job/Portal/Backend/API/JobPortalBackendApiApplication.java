package com.jobportal.Job.Portal.Backend.API;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@Slf4j
@EnableCaching
public class JobPortalBackendApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalBackendApiApplication.class, args);
	}

}
