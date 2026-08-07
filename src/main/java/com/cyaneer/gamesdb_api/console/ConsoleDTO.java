package com.cyaneer.gamesdb_api.console;

public class ConsoleDTO {
    
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
