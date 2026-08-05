package com.cyaneer.gamesdb_api.status;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class StatusModelAssembler implements RepresentationModelAssembler<Status, EntityModel<Status>> {
    
    @Override
    public EntityModel<Status> toModel(Status status) {
        return EntityModel.of(status,
            linkTo(methodOn(StatusController.class).one(status.getId())).withSelfRel(),
            linkTo(methodOn(StatusController.class).all()).withRel("statuses")
        );
    }
}
