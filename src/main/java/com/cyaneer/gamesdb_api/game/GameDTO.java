package com.cyaneer.gamesdb_api.game;

public class GameDTO {
    
    private String title;
    private Long statusId;
    private String console;
    private Double score;

    public GameDTO() {}

    public GameDTO(String title, Long statusId, String console, Double score) {
        this.title = title;
        this.statusId = statusId;
        this.console = console;
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
