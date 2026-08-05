package com.cyaneer.gamesdb_api.game;

import java.util.Objects;

import com.cyaneer.gamesdb_api.status.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Game {
    
    private @Id
    @GeneratedValue Long id;

    private String title;

    @ManyToOne
    private Status status;

    private String console; //TODO: Table for consoles

    Game() {

    }

    Game(String title, Status status, String console) {
        this.title = title;
        this.status = status;
        this.console = console;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public void update(Game newGame) {
        this.setTitle(newGame.getTitle());
        this.setStatus(newGame.getStatus());
        this.setConsole(newGame.getConsole());
    }

    public void update(String title, Status status, String console) {
        this.setTitle(title);
        this.setStatus(status);
        this.setConsole(console);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Game)) {
            return false;
        } else {
            Game game = (Game) o;
            return this.id == game.id &&
                this.title.equals(game.title) &&
                this.status.equals(game.status) &&
                this.console.equals(game.status);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.status, this.console);
    }

    @Override
    public String toString() {
        return "Game{id=" + this.id + ", title=" + this.title + ", status=" + this.status + ", console=" + this.console + "}";
    }
}
