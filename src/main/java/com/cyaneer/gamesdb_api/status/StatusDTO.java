package com.cyaneer.gamesdb_api.status;

public class StatusDTO {
    
    private String name;

    StatusDTO() {}

    StatusDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
