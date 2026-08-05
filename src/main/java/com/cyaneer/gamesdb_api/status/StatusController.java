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
    
    private final StatusRepository repository;
    private final StatusModelAssembler assembler;

    StatusController(StatusRepository repository, StatusModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/statuses")
    ResponseEntity<CollectionModel<EntityModel<Status>>> all() {
        List<EntityModel<Status>> statuses = repository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Status>> collectionModel = CollectionModel
            .of(statuses, linkTo(methodOn(StatusController.class).all()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping("/statuses")
    ResponseEntity<EntityModel<Status>> newStatus(@RequestBody Status newStatus) {
        EntityModel<Status> entityModel = assembler.toModel(repository.save(newStatus));

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @GetMapping("/statuses/{id}")
    ResponseEntity<EntityModel<Status>> one(@PathVariable Long id) {
        Status status = repository.findById(id).orElseThrow(
            () -> {return new StatusNotFoundException(id);}
        );

        EntityModel<Status> entityModel = assembler.toModel(status);

        return ResponseEntity
            .ok(entityModel);
    }

    @PutMapping("/statuses/{id}")
    ResponseEntity<EntityModel<Status>> replaceStatus(@RequestBody Status newStatus, @PathVariable Long id) {
        Status updatedStatus = repository.findById(id).map(
            status -> {
                status.update(newStatus);
                return repository.save(status);
            }
        ).orElseThrow(
            () -> {return new StatusNotFoundException(id);}
        );

        EntityModel<Status> entityModel = assembler.toModel(updatedStatus);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/statuses/{id}")
    ResponseEntity<EntityModel<Status>> deleteStatus(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
