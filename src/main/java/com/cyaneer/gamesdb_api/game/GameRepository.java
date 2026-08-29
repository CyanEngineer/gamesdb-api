package com.cyaneer.gamesdb_api.game;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
    boolean existsByConsoleId(Long consoleId);
    boolean existsByStatusId(Long statusId);
}
