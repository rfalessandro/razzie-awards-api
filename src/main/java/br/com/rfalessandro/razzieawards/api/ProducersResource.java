package br.com.rfalessandro.razzieawards.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import br.com.rfalessandro.razzieawards.service.ProducerService;
import br.com.rfalessandro.razzieawards.model.Producer;
import br.com.rfalessandro.razzieawards.dto.ProducerAwardsIntervalDTO;
import br.com.rfalessandro.razzieawards.dto.MaxMinProducerAwardsIntervalDTO;

@Path("/api/v1/producers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProducersResource {

    @Inject
    ProducerService producerService;


    @GET
    @Path("/award-intervals")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducers() {
        return Response.ok().entity(
            producerService.getMaxMinAwardsInterval()
        ).build();

    }
}
