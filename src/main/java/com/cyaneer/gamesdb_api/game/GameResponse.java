package com.cyaneer.gamesdb_api.game;

public class GameResponse {
    
    private Long gameId;
    private String gameTitle;
    private Long statusId;
    private String statusName;
    private Long consoleId;
    private String consoleName;
    private Double score;

    public GameResponse() {}

    public GameResponse(Long gameId, String gameTitle, Long statusId, String statusName, Long consoleId, String consoleName, Double score) {
        this.gameId = gameId;
        this.gameTitle = gameTitle;
        this.statusId = statusId;
        this.statusName = statusName;
        this.consoleId = consoleId;
        this.consoleName = consoleName;
        this.score = score;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getConsoleId() {
        return consoleId;
    }

    public void setConsoleId(Long consoleId) {
        this.consoleId = consoleId;
    }

    public String getConsoleName() {
        return consoleName;
    }

    public void setConsoleName(String consoleName) {
        this.consoleName = consoleName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
