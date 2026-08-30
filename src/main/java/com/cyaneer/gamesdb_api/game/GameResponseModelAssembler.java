package com.cyaneer.gamesdb_api.game;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class GameResponseModelAssembler implements RepresentationModelAssembler<GameResponse, EntityModel<GameResponse>> {

    @Override
    public EntityModel<GameResponse> toModel(GameResponse gameResponse) {
        return EntityModel.of(gameResponse,
            linkTo(methodOn(GameController.class).one(gameResponse.getGameId())).withSelfRel(),
            linkTo(methodOn(GameController.class).all(Pageable.unpaged())).withRel("games")
        );
    }
    
}
