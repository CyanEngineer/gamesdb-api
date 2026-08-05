package com.cyaneer.gamesdb_api.game;

public class GameResponse {
    
    private Long gameId;
    private String gameTitle;
    private Long statusId;
    private String statusName;
    private String consoleName;

    public GameResponse() {}

    public GameResponse(Long gameId, String gameTitle, Long statusId, String statusName, String consoleName) {
        this.gameId = gameId;
        this.gameTitle = gameTitle;
        this.statusId = statusId;
        this.statusName = statusName;
        this.consoleName = consoleName;
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

    public String getConsoleName() {
        return consoleName;
    }

    public void setConsoleName(String consoleName) {
        this.consoleName = consoleName;
    }

    
}
