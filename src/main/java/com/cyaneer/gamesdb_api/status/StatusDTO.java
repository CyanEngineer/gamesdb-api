package com.cyaneer.gamesdb_api.status;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Status", description = "A play-status that a game can have (e.g. \"Playing\", \"Done\" or \"To do\"")
public class StatusDTO {
    
    @NotBlank
    private String name;

    public StatusDTO() {}

    public StatusDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
