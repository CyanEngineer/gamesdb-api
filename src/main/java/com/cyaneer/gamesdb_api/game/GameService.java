package com.cyaneer.gamesdb_api.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Game> getAllGames(Pageable pageable) {
        return gameRepository.findAll(pageable);
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
        String sortingName = getSortingName(dto.getSortingName(), dto.getTitle());
        Game game = new Game(title, status, console, score, sortingName);

        return gameRepository.save(game);
    }

    @Transactional
    public Game updateGame(Long id, GameDTO dto) {
        String title = dto.getTitle();
        Status status = findStatusById(dto.getStatusId());
        Console console = findConsoleById(dto.getConsoleId());
        Double score = formatScore(dto.getScore());
        String sortingName = getSortingName(dto.getSortingName(), dto.getTitle());
        Game game = findGameById(id);
        
        game.update(title, status, console, score, sortingName);

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
            game.getScore(),
            game.getSortingName()
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

    private String getSortingName(String sortingName, String title) {
        if(sortingName != null && !sortingName.isBlank()) {
            return sortingName;
        } else {
            String lower = title.toLowerCase();
            String noLeadingArticles = lower.replaceAll("^(a |an |the )", "");
            return noLeadingArticles;
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
