package com.cyaneer.gamesdb_api.common;

public class ResourceInUseException extends RuntimeException {
    
    public ResourceInUseException(String resourceType, Long id) {
        super(resourceType + " with id " + id + " is in use and cannot be deleted.");
    }
}
