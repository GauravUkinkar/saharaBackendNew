package com.SaharaAmussmentPark.util;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;


@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
	    return new OpenAPI()
	        .info(new Info()
	            .title("Sahara Amussment")
	            .version("1.0")
	            .description("API documentation for Coffie Project"))
	        .servers(List.of(
	                    new Server().url("http://localhost:9000").description("Deployed Server")
	                ));
	}

}
	