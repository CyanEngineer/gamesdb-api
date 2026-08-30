package com.cyaneer.gamesdb_api.console;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ConsoleResponseModelAssembler implements RepresentationModelAssembler<ConsoleResponse, EntityModel<ConsoleResponse>> {

    @Override
    public EntityModel<ConsoleResponse> toModel(ConsoleResponse console) {
        return EntityModel.of(console,
            linkTo(methodOn(ConsoleController.class).one(console.getId())).withSelfRel(),
            linkTo(methodOn(ConsoleController.class).all(Pageable.unpaged())).withRel("consoles")
        );
    }

    
    
}
