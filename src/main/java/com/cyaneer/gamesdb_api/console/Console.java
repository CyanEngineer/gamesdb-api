package com.cyaneer.gamesdb_api.console;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Console {
    
    private @Id
    @GeneratedValue Long id;

    private String name;

    Console() {}

    Console(String name) {
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

    public void update(String name) {
        this.setName(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Console)) {
            return false;
        } else {
            Console console = (Console) o;
            return this.name.equals(console.getName());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        return "Console{name=" + this.name + "}";
    }
}
