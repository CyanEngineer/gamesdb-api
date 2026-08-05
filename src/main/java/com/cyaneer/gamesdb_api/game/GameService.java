package com.cyaneer.gamesdb_api.game;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cyaneer.gamesdb_api.status.Status;
import com.cyaneer.gamesdb_api.status.StatusNotFoundException;
import com.cyaneer.gamesdb_api.status.StatusRepository;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final StatusRepository statusRepository;

    public GameService(GameRepository gameRepository, StatusRepository statusRepository) {
        this.gameRepository = gameRepository;
        this.statusRepository = statusRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGame(Long id) {
        return gameRepository.findById(id)
            .orElseThrow(() -> new GameNotFoundException(id));
    }

    @Transactional
    public Game createGame(GameDTO dto) {
        Status status = statusRepository.findById(dto.getStatusId())
            .orElseThrow(() -> new StatusNotFoundException(dto.getStatusId()));
        
        Game game = new Game(dto.getTitle(), status, dto.getConsole());
        return gameRepository.save(game);
    }

    @Transactional
    public Game updateGame(Long id, GameDTO dto) {
        Status status = statusRepository.findById(dto.getStatusId())
            .orElseThrow(() -> new StatusNotFoundException(dto.getStatusId()));
        Game game = gameRepository.findById(id)
            .orElseThrow(() -> new GameNotFoundException(id));
        game.update(dto.getTitle(), status, dto.getConsole());

        return gameRepository.save(game);
    }

    @Transactional
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}
