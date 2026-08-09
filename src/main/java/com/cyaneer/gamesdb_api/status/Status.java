package com.cyaneer.gamesdb_api.status;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Status {
    
    private @Id
    @GeneratedValue Long id;

    private String name;

    public Status() {}

    public Status(String name) {
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
        } else if (!(o instanceof Status)) {
            return false;
        } else {
            Status status = (Status) o;
            return this.name.equals(status.getName());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        return "Status{name=" + this.name + "}";
    }
}
