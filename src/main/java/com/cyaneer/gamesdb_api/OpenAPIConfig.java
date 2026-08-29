package com.cyaneer.gamesdb_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {

    @Autowired(required = false)
    private BuildProperties buildProperties;
    
    @Bean
    public OpenAPI gamesDBOpenAPI() {
        String version = buildProperties != null ? buildProperties.getVersion() : "dev";
        return new OpenAPI()
            .info(new Info()
                .title("GamesDB API")
                .version(version)
                .description("RESTful API for games I have played or want to play")
            );
    }
}
