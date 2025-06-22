package com.mycompany.gr01_1bt2_622_25a.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Recurso REST de ejemplo
 */
@Path("rest")
public class JakartaEE8Resource {
    
    @GET
    public Response ping() {
        return Response
                .ok("ping Jakarta EE 10")
                .build();
    }
}