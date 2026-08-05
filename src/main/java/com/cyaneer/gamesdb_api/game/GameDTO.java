package com.cyaneer.gamesdb_api.game;

public class GameDTO {
    
    private String title;
    private Long statusId;
    private String console;

    public GameDTO() {}

    public GameDTO(String title, Long statusId, String console) {
        this.title = title;
        this.statusId = statusId;
        this.console = console;
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
}
