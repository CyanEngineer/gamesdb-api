package com.cyaneer.gamesdb_api.status;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class StatusResponseModelAssembler implements RepresentationModelAssembler<StatusResponse, EntityModel<StatusResponse>> {
    
    @Override
    public EntityModel<StatusResponse> toModel(StatusResponse status) {
        return EntityModel.of(status,
            linkTo(methodOn(StatusController.class).one(status.getId())).withSelfRel(),
            linkTo(methodOn(StatusController.class).all()).withRel("statuses")
        );
    }
}
