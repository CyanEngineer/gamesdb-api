package com.cyaneer.gamesdb_api.game;

public class GameDTO {
    
    private String title;
    private Long statusId;
    private Long consoleId;
    private Double score;

    public GameDTO() {}

    public GameDTO(String title, Long statusId, Long consoleId, Double score) {
        this.title = title;
        this.statusId = statusId;
        this.consoleId = consoleId;
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

    public Long getConsoleId() {
        return consoleId;
    }

    public void setConsoleId(Long consoleId) {
        this.consoleId = consoleId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
