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

@RestController
public class StatusController {
    
    private final StatusService service;
    private final StatusResponseModelAssembler responseAssembler;

    StatusController(StatusService service, StatusResponseModelAssembler assembler) {
        this.service = service;
        this.responseAssembler = assembler;
    }

    @GetMapping("/statuses")
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
    ResponseEntity<EntityModel<StatusResponse>> newStatus(@RequestBody StatusDTO dto) {
        StatusResponse statusResponse = service.mapToResponse(service.createNewStatus(dto));
        EntityModel<StatusResponse> entityModel = responseAssembler.toModel(statusResponse);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @GetMapping("/statuses/{id}")
    ResponseEntity<EntityModel<StatusResponse>> one(@PathVariable Long id) {
        StatusResponse statusResponse = service.mapToResponse(service.getStatus(id));
        EntityModel<StatusResponse> entityModel = responseAssembler.toModel(statusResponse);

        return ResponseEntity
            .ok(entityModel);
    }

    @PutMapping("/statuses/{id}")
    ResponseEntity<EntityModel<StatusResponse>> replaceStatus(@RequestBody StatusDTO dto, @PathVariable Long id) {
        StatusResponse updatedStatus = service.mapToResponse(service.updateStatus(id, dto));
        EntityModel<StatusResponse> entityModel = responseAssembler.toModel(updatedStatus);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/statuses/{id}")
    ResponseEntity<EntityModel<StatusResponse>> deleteStatus(@PathVariable Long id) {
        service.deleteStatus(id);

        return ResponseEntity.noContent().build();
    }
}
