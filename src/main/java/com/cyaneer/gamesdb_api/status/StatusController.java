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
    private final StatusModelAssembler assembler;

    StatusController(StatusService service, StatusModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/statuses")
    ResponseEntity<CollectionModel<EntityModel<Status>>> all() {
        List<EntityModel<Status>> statuses = service.getAllStatuses().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Status>> collectionModel = CollectionModel
            .of(statuses, linkTo(methodOn(StatusController.class).all()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping("/statuses")
    ResponseEntity<EntityModel<Status>> newStatus(@RequestBody StatusDTO dto) {
        Status status = service.createNewStatus(dto);
        EntityModel<Status> entityModel = assembler.toModel(status);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @GetMapping("/statuses/{id}")
    ResponseEntity<EntityModel<Status>> one(@PathVariable Long id) {
        Status status = service.getStatus(id);
        EntityModel<Status> entityModel = assembler.toModel(status);

        return ResponseEntity
            .ok(entityModel);
    }

    @PutMapping("/statuses/{id}")
    ResponseEntity<EntityModel<Status>> replaceStatus(@RequestBody StatusDTO dto, @PathVariable Long id) {
        Status updatedStatus = service.updateStatus(id, dto);
        EntityModel<Status> entityModel = assembler.toModel(updatedStatus);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/statuses/{id}")
    ResponseEntity<EntityModel<Status>> deleteStatus(@PathVariable Long id) {
        service.deleteStatus(id);

        return ResponseEntity.noContent().build();
    }
}
