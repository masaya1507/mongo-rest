package jp.projects.miya.mongo_rest.service;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jp.projects.miya.mongo_rest.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@Path("/")
public class UserService {
	@Autowired
	MongoOperations mongoOperation;

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response find(
    		@PathParam("username") String username) {

		Query searchUserQuery = new Query(Criteria.where("username").is(username));

		List<User> listUser = this.mongoOperation.find(searchUserQuery, User.class);

        return Response.ok().entity(listUser).build();
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public Response edit(User input) {
    	this.mongoOperation.save(input);

        return Response.ok().entity(input).build();
    }
}

