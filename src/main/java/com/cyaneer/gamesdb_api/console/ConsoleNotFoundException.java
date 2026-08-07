package com.cyaneer.gamesdb_api.console;

public class ConsoleNotFoundException extends RuntimeException {

    public ConsoleNotFoundException(Long id) {
        super("Could not find console " + id);
    }
    
}
