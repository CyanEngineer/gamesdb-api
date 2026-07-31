package com.cyaneer.gamesdb_api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    
    private final GameRepository repository;

    GameController(GameRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/games")
    List<Game> all() {
        return repository.findAll();
    }

    @PostMapping("/games")
    Game newGame(@RequestBody Game newGame) {
        return repository.save(newGame);
    }

    @GetMapping("/games/{id}")
    Game one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(
            () -> {return new GameNotFoundException(id);}
        );
    }

    @PutMapping("/games/{id}")
    Game replaceGame(@RequestBody Game newGame, @PathVariable Long id) {

        return repository.findById(id).map(
            game -> {
                game.setTitle(newGame.getTitle());
                game.setStatus(newGame.getStatus());
                game.setConsole(newGame.getConsole());
                return repository.save(game);
            }
        )
        .orElseGet(
            () -> {return repository.save(newGame);}
        );
    }

    @DeleteMapping("/games/{id}")
    void deleteGame(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
