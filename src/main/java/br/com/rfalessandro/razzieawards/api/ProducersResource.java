package br.com.rfalessandro.razzieawards.api;

import br.com.rfalessandro.razzieawards.dto.MaxMinProducerAwardsIntervalDTO;
import br.com.rfalessandro.razzieawards.exception.AwardCategoryNotFoundException;
import br.com.rfalessandro.razzieawards.service.ProducerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/v1/producers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProducersResource {

    @Inject ProducerService producerService;

    @GET
    @Path("/award-intervals/worst-movie")
    @Produces(MediaType.APPLICATION_JSON)
    public MaxMinProducerAwardsIntervalDTO getProducers() {
        return producerService.getMaxMinAwardsInterval();
    }

    @GET
    @Path("/award-intervals/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public MaxMinProducerAwardsIntervalDTO getProducers2(@PathParam("category") String category) {
        throw new AwardCategoryNotFoundException(category);
    }
}
