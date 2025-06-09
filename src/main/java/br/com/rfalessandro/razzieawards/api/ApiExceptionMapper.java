package br.com.rfalessandro.razzieawards.api;

import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;


@Provider
@Slf4j
public class ApiExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        log.error("Error processing request", exception);
        return Response.serverError().entity(exception.getMessage()).build();
    }
}
