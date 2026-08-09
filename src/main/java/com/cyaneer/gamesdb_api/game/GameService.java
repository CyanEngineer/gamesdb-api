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
        Status status = findStatusById(dto.getStatusId());
        Console console = findConsoleById(dto.getConsoleId());
        Game game = new Game(dto.getTitle(), status, console, dto.getScore());

        return gameRepository.save(game);
    }

    @Transactional
    public Game updateGame(Long id, GameDTO dto) {
        Console console = findConsoleById(dto.getConsoleId());
        Status status = findStatusById(dto.getStatusId());
        Game game = findGameById(id);
        game.update(dto.getTitle(), status, console, dto.getScore());

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
