package com.cyaneer.gamesdb_api.console;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

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

import jakarta.validation.Valid;

@RestController
public class ConsoleController {
    
    private ConsoleService service;
    private ConsoleResponseModelAssembler modelAssembler;

    public ConsoleController(ConsoleService service, ConsoleResponseModelAssembler modelAssembler) {
        this.service = service;
        this.modelAssembler = modelAssembler;
    }

    @GetMapping("/consoles")
    public ResponseEntity<CollectionModel<EntityModel<ConsoleResponse>>> all() {
        List<EntityModel<ConsoleResponse>> consoles = service.getAllConsoles().stream()
            .map(service::mapToResponse)
            .map(modelAssembler::toModel)
            .collect(Collectors.toList());
        
        CollectionModel<EntityModel<ConsoleResponse>> collectionModel = CollectionModel
            .of(consoles, linkTo(methodOn(ConsoleController.class).all()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping("/consoles")
    public ResponseEntity<EntityModel<ConsoleResponse>> newConsole(@Valid @RequestBody ConsoleDTO dto) {
        Console console = service.createConsole(dto);
        EntityModel<ConsoleResponse> entityModel = modelAssembler.toModel(service.mapToResponse(console));

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @GetMapping("/consoles/{id}")
    public ResponseEntity<EntityModel<ConsoleResponse>> one(@PathVariable Long id) {
        Console console = service.getConsole(id);
        EntityModel<ConsoleResponse> entityModel = modelAssembler.toModel(service.mapToResponse(console));

        return ResponseEntity
            .ok(entityModel);
    }

    @PutMapping("/consoles/{id}")
    public ResponseEntity<EntityModel<ConsoleResponse>> replaceConsole(@PathVariable Long id, @Valid @RequestBody ConsoleDTO dto) {
        Console console = service.updateConsole(id, dto);
        EntityModel<ConsoleResponse> entityModel = modelAssembler.toModel(service.mapToResponse(console));

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/consoles/{id}")
    public ResponseEntity<EntityModel<ConsoleResponse>> deleteConsole(@PathVariable Long id) {
        service.deleteConsole(id);

        return ResponseEntity.noContent().build();
    }
}
