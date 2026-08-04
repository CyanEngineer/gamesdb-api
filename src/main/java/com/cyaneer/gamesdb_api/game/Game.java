package com.cyaneer.gamesdb_api.game;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Game {
    
    private @Id
    @GeneratedValue Long id;

    private String title;
    private String status;
    private String console; //TODO: Table for consoles

    Game() {

    }

    Game(String title, String status, String console) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
