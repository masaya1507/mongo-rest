package jp.projects.miya.mongo_rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
@Path("/")
public class MapReduceService {
	@Autowired
	MongoOperations mongoOperation;

    @GET
    @Path("/{input}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response ping(@PathParam("input") String input) {
    	// this.mongoOperation.mapReduce(arg0, arg1, arg2, arg3);

    	return Response.ok().entity(null).build();
    }
}
