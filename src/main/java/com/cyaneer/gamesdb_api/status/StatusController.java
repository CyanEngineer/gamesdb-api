package com.cyaneer.gamesdb_api.status;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Statuses", description = "API endpoints for interacting with statuses. A status can't be deleted if any games are using it.")
public class StatusController {
    
    private final StatusService service;
    private final StatusResponseModelAssembler responseAssembler;

    StatusController(StatusService service, StatusResponseModelAssembler assembler) {
        this.service = service;
        this.responseAssembler = assembler;
    }

    @GetMapping("/statuses")
    @Operation(
        summary = "Get all statuses",
        description = "Returns all statuses in the database"
    )
    ResponseEntity<CollectionModel<EntityModel<StatusResponse>>> all() {
        List<EntityModel<StatusResponse>> statuses = service.getAllStatuses().stream()
            .map(service::mapToResponse)
            .map(responseAssembler::toModel)
            .collect(Collectors.toList());
        
        CollectionModel<EntityModel<StatusResponse>> collectionModel = CollectionModel
            .of(statuses, linkTo(methodOn(StatusController.class).all()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping("/statuses")
    @Operation(
        summary = "Add a new status",
        description = "Create a new status based on the request body"
    )
    ResponseEntity<EntityModel<StatusResponse>> newStatus(@Valid @RequestBody StatusDTO dto) {
        StatusResponse statusResponse = service.mapToResponse(service.createStatus(dto));
        EntityModel<StatusResponse> entityModel = responseAssembler.toModel(statusResponse);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @GetMapping("/statuses/{id}")
    @Operation(
        summary = "Get one status",
        description = "Returns the specified status"
    )
    ResponseEntity<EntityModel<StatusResponse>> one(@PathVariable Long id) {
        StatusResponse statusResponse = service.mapToResponse(service.getStatus(id));
        EntityModel<StatusResponse> entityModel = responseAssembler.toModel(statusResponse);

        return ResponseEntity
            .ok(entityModel);
    }

    @PutMapping("/statuses/{id}")
    @Operation(
        summary = "Update a status",
        description = "Update the specified status based on the request body"
    )
    ResponseEntity<EntityModel<StatusResponse>> replaceStatus(@PathVariable Long id, @Valid @RequestBody StatusDTO dto) {
        StatusResponse updatedStatus = service.mapToResponse(service.updateStatus(id, dto));
        EntityModel<StatusResponse> entityModel = responseAssembler.toModel(updatedStatus);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/statuses/{id}")
    @Operation(
        summary = "Delete a status",
        description = "Delete the specified console from the status"
    )
    ResponseEntity<EntityModel<StatusResponse>> deleteStatus(@PathVariable Long id) {
        service.deleteStatus(id);

        return ResponseEntity.noContent().build();
    }
}
