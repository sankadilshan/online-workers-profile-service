package com.onlineWorkers.onlineWorkersProfileService;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EntityScan(basePackages = "com.onlineworkers.models")
@ComponentScan(basePackages = {"com.onlineWorkers.onlineWorkersProfileService.controller",
		                        "com.onlineWorkers.onlineWorkersProfileService.service",
		                        "com.onlineWorkers.onlineWorkersProfileService.configuration",
		                        "com.onlineWorkers.onlineWorkersProfileService.security",
		                        "com.onlineWorkers.onlineWorkersProfileService.interceptor"
})
public class OnlineWorkersProfileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineWorkersProfileServiceApplication.class, args);
	}


	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Bean
	public CorsFilter corsFilter(){
		final UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
		final CorsConfiguration corsConfiguration=new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedOrigin("*"); //need to set it localhost:4200
		corsConfiguration.addAllowedMethod("OPTIONS");
		corsConfiguration.addAllowedMethod("GET");
		corsConfiguration.addAllowedMethod("POST");
		corsConfiguration.addAllowedMethod("PUT");
		corsConfiguration.addAllowedMethod("DELETE");
		corsConfiguration.addAllowedMethod("PATCH");
		source.registerCorsConfiguration("/**",corsConfiguration);
		return new CorsFilter(source);
	}
}
