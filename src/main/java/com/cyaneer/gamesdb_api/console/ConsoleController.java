package com.cyaneer.gamesdb_api.console;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
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
@Tag(name = "Consoles", description = "API endpoints for interacting with consoles. A console can't be deleted if any games are using it.")
public class ConsoleController {
    
    private ConsoleService service;
    private ConsoleResponseModelAssembler modelAssembler;

    public ConsoleController(ConsoleService service, ConsoleResponseModelAssembler modelAssembler) {
        this.service = service;
        this.modelAssembler = modelAssembler;
    }

    @GetMapping("/consoles")
    @Operation(summary = "Get all consoles", description = "Returns all consoles in the database")
    @ApiResponse(
        responseCode = "200",
        description = "All consoles in the database",
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ConsoleResponse.class))
        )
    )   
    ResponseEntity<PagedModel<EntityModel<ConsoleResponse>>> all(@ParameterObject Pageable pageable) {
        Page<Console> consoles = service.getAllConsoles(pageable);

        PagedModel<EntityModel<ConsoleResponse>> pagedModel = PagedModel.of(
            consoles.map(service::mapToResponse).map(modelAssembler::toModel).toList(),
            new PageMetadata(
                pageable.getPageSize(), 
                consoles.getNumber(), 
                consoles.getNumberOfElements()),
            linkTo(methodOn(ConsoleController.class).all(pageable)).withSelfRel()
        );

        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping("/consoles")
    @Operation(
        summary = "Add a new console",
        description = "Create a new console based on the request body"
    )
    @ApiResponse(
        responseCode = "201",
        description = "The created console",
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ConsoleResponse.class))
        )
    )  
    ResponseEntity<EntityModel<ConsoleResponse>> newConsole(@Valid @RequestBody ConsoleDTO dto) {
        Console console = service.createConsole(dto);
        EntityModel<ConsoleResponse> entityModel = modelAssembler.toModel(service.mapToResponse(console));

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @GetMapping("/consoles/{id}")
    @Operation(
        summary = "Get one console",
        description = "Returns the specified console"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The specified console",
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ConsoleResponse.class))
        )
    )  
    ResponseEntity<EntityModel<ConsoleResponse>> one(@PathVariable Long id) {
        Console console = service.getConsole(id);
        EntityModel<ConsoleResponse> entityModel = modelAssembler.toModel(service.mapToResponse(console));

        return ResponseEntity
            .ok(entityModel);
    }

    @PutMapping("/consoles/{id}")
    @Operation(
        summary = "Update a console",
        description = "Update the specified console based on the request body"
    )
    @ApiResponse(
        responseCode = "201",
        description = "The updated console",
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ConsoleResponse.class))
        )
    )  
    ResponseEntity<EntityModel<ConsoleResponse>> replaceConsole(@PathVariable Long id, @Valid @RequestBody ConsoleDTO dto) {
        Console console = service.updateConsole(id, dto);
        EntityModel<ConsoleResponse> entityModel = modelAssembler.toModel(service.mapToResponse(console));

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/consoles/{id}")
    @Operation(
        summary = "Delete a console",
        description = "Delete the specified console from the database"
    )
    @ApiResponse(
        responseCode = "204",
        description = "No Content (successfully deleted)",
        content = @Content()
    )  
    ResponseEntity<EntityModel<ConsoleResponse>> deleteConsole(@PathVariable Long id) {
        service.deleteConsole(id);

        return ResponseEntity.noContent().build();
    }
}
