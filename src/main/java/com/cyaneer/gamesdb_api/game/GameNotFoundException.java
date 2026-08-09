package com.cyaneer.gamesdb_api.game;

import com.cyaneer.gamesdb_api.common.ResourceNotFoundException;

public class GameNotFoundException extends ResourceNotFoundException {
    
    public GameNotFoundException(Long id) {
        super("Could not find game " + id);
    }
}
