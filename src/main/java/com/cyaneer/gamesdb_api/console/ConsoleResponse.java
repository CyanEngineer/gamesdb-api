package com.cyaneer.gamesdb_api.console;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ConsoleResponse", description = "The console where the game was played")
public class ConsoleResponse {
    
    private Long id;
    private String name;
    
    public ConsoleResponse() {
    }

    public ConsoleResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}
