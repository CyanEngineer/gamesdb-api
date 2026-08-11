package com.cyaneer.gamesdb_api.game;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GameDTO {
    
    @NotBlank(message = "title is required")
    private String title;

    @NotNull(message = "statusId is required")
    private Long statusId;

    @NotNull(message = "consoleId is required")
    private Long consoleId;

    @DecimalMin(value = "0.0", message = "score must be at least 0")
    @DecimalMax(value = "10.0", message = "score must be at most 10")
    private Double score;

    private String sortingName;

    public GameDTO() {}

    public GameDTO(String title, Long statusId, Long consoleId, Double score, String sortingName) {
        this.title = title;
        this.statusId = statusId;
        this.consoleId = consoleId;
        this.score = score;
        this.sortingName = sortingName;
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

    public String getSortingName() {
        return sortingName;
    }

    public void setSortingName(String sortingName) {
        this.sortingName = sortingName;
    }
}
