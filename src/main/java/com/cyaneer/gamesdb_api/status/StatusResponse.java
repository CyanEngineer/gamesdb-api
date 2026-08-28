package com.cyaneer.gamesdb_api.status;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "StatusResponse", description = "A play-status that a game can have (e.g. \"Playing\", \"Done\" or \"To do\"")
public class StatusResponse {
    
    private Long id;
    private String name;

    public StatusResponse() {
        
    }

    public StatusResponse(Long id, String name) {
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
