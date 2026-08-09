package com.cyaneer.gamesdb_api.console;

import com.cyaneer.gamesdb_api.common.ResourceNotFoundException;

public class ConsoleNotFoundException extends ResourceNotFoundException {

    public ConsoleNotFoundException(Long id) {
        super("Could not find console " + id);
    }
    
}
