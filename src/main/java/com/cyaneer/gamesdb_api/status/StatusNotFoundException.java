package com.cyaneer.gamesdb_api.status;

public class StatusNotFoundException extends RuntimeException {
    
    public StatusNotFoundException(Long id) {
        super("Could not find status " + id);
    }
}
