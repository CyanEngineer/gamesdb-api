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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Games", description = "API endpoints for interacting with games. There must exist at least one console and status before a game can be created")
public class GameController {
    
    private final GameService service;
    private final GameResponseModelAssembler responseAssembler;

    GameController(GameService service, GameResponseModelAssembler responseAssembler) {
        this.service = service;
        this.responseAssembler = responseAssembler;
    }

    @GetMapping("/games")
    @Operation(
        summary = "Get all games",
        description = "Returns all games in the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "All games in the database",
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = GameResponse.class))
        )
    )   
    ResponseEntity<CollectionModel<EntityModel<GameResponse>>> all() {
        List<EntityModel<GameResponse>> entityModels = service.getAllGames().stream()
            .map(service::mapToResponse)
            .map(responseAssembler::toModel)
            .collect(Collectors.toList());

        CollectionModel<EntityModel<GameResponse>> collectionModel = CollectionModel
            .of(entityModels, linkTo(methodOn(GameController.class).all()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping("/games")
    @Operation(
        summary = "Add a new game",
        description = "Create a new game based on the request body"
    )
    @ApiResponse(
        responseCode = "201",
        description = "The created game",
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = GameResponse.class))
        )
    )  
    ResponseEntity<EntityModel<GameResponse>> newGame(@Valid @RequestBody GameDTO dto) {
        GameResponse gameResponse = service.mapToResponse(service.createGame(dto));
        EntityModel<GameResponse> entityModel = responseAssembler.toModel(gameResponse);
        
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @GetMapping("/games/{id}")
    @Operation(
        summary = "Get one game",
        description = "Returns the specified game"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The specified game",
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = GameResponse.class))
        )
    )  
    ResponseEntity<EntityModel<GameResponse>> one(@PathVariable Long id) {
        GameResponse gameResponse = service.mapToResponse(service.getGame(id));
        EntityModel<GameResponse> entityModel = responseAssembler.toModel(gameResponse);

        return ResponseEntity
            .ok(entityModel);
    }

    @PutMapping("/games/{id}")
    @Operation(
        summary = "Update a game",
        description = "Update the specified game based on the request body"
    )
    @ApiResponse(
        responseCode = "201",
        description = "The updated game",
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = GameResponse.class))
        )
    )  
    ResponseEntity<EntityModel<GameResponse>> replaceGame(@PathVariable Long id, @Valid @RequestBody GameDTO dto) {
        GameResponse updatedGame = service.mapToResponse(service.updateGame(id, dto));
        EntityModel<GameResponse> entityModel = responseAssembler.toModel(updatedGame);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/games/{id}")
    @Operation(
        summary = "Delete a game",
        description = "Delete the specified game from the database"
    )
    @ApiResponse(
        responseCode = "204",
        description = "No Content (successfully deleted)",
        content = @Content()
    )  
    ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        service.deleteGame(id);

        return ResponseEntity.noContent().build();
    }
}
