package com.cyaneer.gamesdb_api.game;

public class GameNotFoundException extends RuntimeException {
    
    public GameNotFoundException(Long id) {
        super("Could not find game " + id);
    }
}
