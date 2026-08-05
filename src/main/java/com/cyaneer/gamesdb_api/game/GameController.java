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
    private final GameResponseModelAssembler responseAssembler;

    GameController(GameService service, GameResponseModelAssembler responseAssembler) {
        this.service = service;
        this.responseAssembler = responseAssembler;
    }

    @GetMapping("/games")
    ResponseEntity<CollectionModel<EntityModel<GameResponse>>> all() {
        List<Game> games = service.getAllGames();

        List<EntityModel<GameResponse>> entityModels = games.stream()
            .map(service::mapToResponse)
            .map(responseAssembler::toModel)
            .collect(Collectors.toList());

        CollectionModel<EntityModel<GameResponse>> collectionModel = CollectionModel
            .of(entityModels, linkTo(methodOn(GameController.class).all()).withSelfRel());

        return ResponseEntity
            .ok(collectionModel);
    }

    @PostMapping("/games")
    ResponseEntity<EntityModel<GameResponse>> newGame(@RequestBody GameDTO dto) {
        GameResponse gameResponse = service.mapToResponse(service.createGame(dto));
        EntityModel<GameResponse> entityModel = responseAssembler.toModel(gameResponse);
        
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @GetMapping("/games/{id}")
    ResponseEntity<EntityModel<GameResponse>> one(@PathVariable Long id) {
        GameResponse gameResponse = service.mapToResponse(service.getGame(id));
        EntityModel<GameResponse> entityModel = responseAssembler.toModel(gameResponse);

        return ResponseEntity
            .ok(entityModel);
    }

    @PutMapping("/games/{id}")
    ResponseEntity<EntityModel<GameResponse>> replaceGame(@RequestBody GameDTO dto, @PathVariable Long id) {
        GameResponse updatedGame = service.mapToResponse(service.updateGame(id, dto));
        EntityModel<GameResponse> entityModel = responseAssembler.toModel(updatedGame);

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
