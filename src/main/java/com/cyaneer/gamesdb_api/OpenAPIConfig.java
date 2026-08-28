package com.cyaneer.gamesdb_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {
    
    @Bean
    public OpenAPI gamesDBOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("GamesDB API")
                .version("1.3.2")
                .description("RESTful API for games I have played or want to play")
            );
    }
}
