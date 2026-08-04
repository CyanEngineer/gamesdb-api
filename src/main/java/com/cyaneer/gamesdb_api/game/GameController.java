package com.cyaneer.gamesdb_api.game;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<CollectionModel<EntityModel<Game>>> all() {
        List<EntityModel<Game>> games = repository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        CollectionModel<EntityModel<Game>> collectionModel = CollectionModel
            .of(games, linkTo(methodOn(GameController.class).all()).withSelfRel());

        return ResponseEntity
            .ok(collectionModel);
    }

    @PostMapping("/games")
    ResponseEntity<EntityModel<Game>> newGame(@RequestBody Game newGame) {
        EntityModel<Game> entityModel = assembler.toModel(repository.save(newGame));
        
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @GetMapping("/games/{id}")
    ResponseEntity<EntityModel<Game>> one(@PathVariable Long id) {
        Game game = repository.findById(id).orElseThrow(
            () -> {return new GameNotFoundException(id);}
        );

        EntityModel<Game> entityModel = assembler.toModel(game);

        return ResponseEntity
            .ok(entityModel);
    }

    @PutMapping("/games/{id}")
    ResponseEntity<EntityModel<Game>> replaceGame(@RequestBody Game newGame, @PathVariable Long id) {
        Game updatedGame = repository.findById(id).map(
            game -> {
                game.update(newGame);
                return repository.save(game);
            }
        )
        .orElseGet(
            () -> {return repository.save(newGame);}
        );

        EntityModel<Game> entityModel = assembler.toModel(updatedGame);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/games/{id}")
    ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
