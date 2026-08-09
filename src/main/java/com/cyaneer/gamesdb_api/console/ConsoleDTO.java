package com.cyaneer.gamesdb_api.console;

import jakarta.validation.constraints.NotBlank;

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
