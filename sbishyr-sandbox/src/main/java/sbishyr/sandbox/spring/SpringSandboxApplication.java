package sbishyr.sandbox.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "sbishyr.sandbox.spring")
public class SpringSandboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSandboxApplication.class, args);
	}
}
