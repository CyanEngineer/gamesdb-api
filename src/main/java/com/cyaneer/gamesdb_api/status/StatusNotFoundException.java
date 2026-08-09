package com.cyaneer.gamesdb_api.status;

import com.cyaneer.gamesdb_api.common.ResourceNotFoundException;

public class StatusNotFoundException extends ResourceNotFoundException {
    
    public StatusNotFoundException(Long id) {
        super("Could not find status " + id);
    }
}
