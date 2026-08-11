package com.cyaneer.gamesdb_api.game;

import java.util.Objects;

import com.cyaneer.gamesdb_api.console.Console;
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

    @ManyToOne
    private Console console;

    private Double score;

    private String sortingName;

    public Game() {

    }

    public Game(String title, Status status, Console console, Double score, String sortingName) {
        this.title = title;
        this.status = status;
        this.console = console;
        this.score = score;
        this.sortingName = sortingName;
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

    public Console getConsole() {
        return console;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getSortingName() {
        return sortingName;
    }

    public void setSortingName(String sortingName) {
        this.sortingName = sortingName;
    }

    public void update(String title, Status status, Console console, Double score, String sortingName) {
        this.setTitle(title);
        this.setStatus(status);
        this.setConsole(console);
        this.setScore(score);
        this.setSortingName(sortingName);
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
                this.console.equals(game.console) &&
                this.score.equals(game.score) &&
                this.sortingName.equals(game.sortingName);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.status, this.console, this.score, this.sortingName);
    }

    @Override
    public String toString() {
        return "Game{id=" + this.id + ", title=" + this.title + ", status=" + this.status + ", console=" + this.console + ", score=" + this.score + ", sortingName=" + this.sortingName + "}";
    }
}
