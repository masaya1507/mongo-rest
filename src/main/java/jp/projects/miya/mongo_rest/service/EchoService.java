package jp.projects.miya.mongo_rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

@Service
@Path("/")
public class EchoService {

    @GET
    @Path("/{input}")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping(@PathParam("input") String input) {
        return input;
    }
}
