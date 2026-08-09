package com.cyaneer.gamesdb_api.game;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cyaneer.gamesdb_api.console.Console;
import com.cyaneer.gamesdb_api.console.ConsoleNotFoundException;
import com.cyaneer.gamesdb_api.console.ConsoleRepository;
import com.cyaneer.gamesdb_api.status.Status;
import com.cyaneer.gamesdb_api.status.StatusNotFoundException;
import com.cyaneer.gamesdb_api.status.StatusRepository;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final StatusRepository statusRepository;
    private final ConsoleRepository consoleRepository;

    public GameService(GameRepository gameRepository, StatusRepository statusRepository, ConsoleRepository consoleRepository) {
        this.gameRepository = gameRepository;
        this.statusRepository = statusRepository;
        this.consoleRepository = consoleRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGame(Long id) {
        return findGameById(id);
    }

    @Transactional
    public Game createGame(GameDTO dto) {
        String title = dto.getTitle();
        Status status = findStatusById(dto.getStatusId());
        Console console = findConsoleById(dto.getConsoleId());
        Double score = formatScore(dto.getScore());
        Game game = new Game(title, status, console, score);

        return gameRepository.save(game);
    }

    @Transactional
    public Game updateGame(Long id, GameDTO dto) {
        String title = dto.getTitle();
        Console console = findConsoleById(dto.getConsoleId());
        Status status = findStatusById(dto.getStatusId());
        Game game = findGameById(id);
        Double score = formatScore(dto.getScore());
        game.update(title, status, console, score);

        return gameRepository.save(game);
    }

    @Transactional
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

    public GameResponse mapToResponse(Game game) {
        return new GameResponse(
            game.getId(), 
            game.getTitle(), 
            game.getStatus().getId(), 
            game.getStatus().getName(),
            game.getConsole().getId(),
            game.getConsole().getName(),
            game.getScore()
        );
    }

    private Double formatScore(Double score) {
        if(score == null) {
            return null;
        } else if (score < 0.1) {
            return 0.0; // Avoids -0.0
        } else {
            return Math.round(score * 10) / 10.0;
        }
    }

    private Game findGameById(Long id) {
        return gameRepository.findById(id)
            .orElseThrow(() -> new GameNotFoundException(id));
    }

    private Status findStatusById(Long id) {
        return statusRepository.findById(id)
            .orElseThrow(() -> new StatusNotFoundException(id));
    }

    private Console findConsoleById(Long id) {
        return consoleRepository.findById(id)
            .orElseThrow(() -> new ConsoleNotFoundException(id));
    }
}
