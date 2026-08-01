package com.cyaneer.gamesdb_api;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
    private final GameModelAssembler assembler;

    GameController(GameRepository repository, GameModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/games")
    CollectionModel<EntityModel<Game>> all() {
        List<EntityModel<Game>> games = repository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(games, linkTo(methodOn(GameController.class).all()).withSelfRel());
    }

    @PostMapping("/games")
    EntityModel<Game> newGame(@RequestBody Game newGame) {
        Game savedGame = repository.save(newGame);
        return assembler.toModel(savedGame);
    }

    @GetMapping("/games/{id}")
    EntityModel<Game> one(@PathVariable Long id) {
        Game game = repository.findById(id).orElseThrow(
            () -> {return new GameNotFoundException(id);}
        );
        return assembler.toModel(game);
    }

    @PutMapping("/games/{id}")
    EntityModel<Game> replaceGame(@RequestBody Game newGame, @PathVariable Long id) {
        Game updatedGame = repository.findById(id).map(
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
        return assembler.toModel(updatedGame);
    }

    @DeleteMapping("/games/{id}")
    void deleteGame(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
