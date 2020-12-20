package com.foxrain.sheep.whileblack;

import com.foxrain.sheep.whileblack.config.configuration.security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class WhileblackApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhileblackApplication.class, args);
	}

}
