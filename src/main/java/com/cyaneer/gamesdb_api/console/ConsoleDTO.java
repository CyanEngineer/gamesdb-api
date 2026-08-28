package com.cyaneer.gamesdb_api.console;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Console", description = "The console where the game was played")
public class ConsoleDTO {
    
    @NotBlank
    private String name;

    public ConsoleDTO() {}

    public ConsoleDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
