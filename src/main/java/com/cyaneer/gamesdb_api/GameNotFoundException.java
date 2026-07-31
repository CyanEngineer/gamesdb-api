package com.cyaneer.gamesdb_api;

public class GameNotFoundException extends RuntimeException {
    
    GameNotFoundException(Long id) {
        super("Could not find game " + id);
    }
}
