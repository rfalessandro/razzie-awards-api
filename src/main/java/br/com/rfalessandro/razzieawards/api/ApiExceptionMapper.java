package br.com.rfalessandro.razzieawards.api;

import br.com.rfalessandro.razzieawards.exception.AwardCategoryNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;

@Provider
@Slf4j
@Schema(
        description = "Error response",
        name = "ApiError",
        properties = {
            @SchemaProperty(name = "code"),
            @SchemaProperty(name = "status"),
            @SchemaProperty(name = "message")
        })
public class ApiExceptionMapper implements ExceptionMapper<Throwable> {

    private Response returnErrorResponse(String message, Response.Status status) {
        Map<String, String> response = new java.util.HashMap<>();
        response.put("message", message);
        response.put("code", String.valueOf(status.getStatusCode()));
        response.put("status", status.getReasonPhrase());
        return Response.status(status).entity(response).build();
    }

    @Override
    public Response toResponse(Throwable exception) {
        log.error("Error processing request", exception);
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        if (exception instanceof AwardCategoryNotFoundException) {
            status = Response.Status.NOT_FOUND;
        }
        return returnErrorResponse(exception.getMessage(), status);
    }
}
