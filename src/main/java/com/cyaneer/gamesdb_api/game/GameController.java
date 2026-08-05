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
    
    private final GameService service;
    private final GameModelAssembler assembler;

    GameController(GameService service, GameModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/games")
    ResponseEntity<CollectionModel<EntityModel<Game>>> all() {
        List<Game> games = service.getAllGames();

        List<EntityModel<Game>> entityModels = games.stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        CollectionModel<EntityModel<Game>> collectionModel = CollectionModel
            .of(entityModels, linkTo(methodOn(GameController.class).all()).withSelfRel());

        return ResponseEntity
            .ok(collectionModel);
    }

    @PostMapping("/games")
    ResponseEntity<EntityModel<Game>> newGame(@RequestBody GameDTO dto) {
        Game game = service.createGame(dto);
        EntityModel<Game> entityModel = assembler.toModel(game);
        
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @GetMapping("/games/{id}")
    ResponseEntity<EntityModel<Game>> one(@PathVariable Long id) {
        Game game = service.getGame(id);
        EntityModel<Game> entityModel = assembler.toModel(game);

        return ResponseEntity
            .ok(entityModel);
    }

    @PutMapping("/games/{id}")
    ResponseEntity<EntityModel<Game>> replaceGame(@RequestBody GameDTO dto, @PathVariable Long id) {
        Game updatedGame = service.updateGame(id, dto);
        EntityModel<Game> entityModel = assembler.toModel(updatedGame);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/games/{id}")
    ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        service.deleteGame(id);

        return ResponseEntity.noContent().build();
    }
}
