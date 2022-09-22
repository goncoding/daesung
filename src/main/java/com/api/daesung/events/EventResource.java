package com.api.daesung.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class EventResource extends EntityModel<Event> {

    public EventResource(Event evnet, Link... links) {
        super(evnet, links);
        add(linkTo(EventController.class).slash(evnet.getId()).withSelfRel());

    }

}
