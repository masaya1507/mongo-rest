package jp.projects.miya.mongo_rest.service;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;

import com.mongodb.gridfs.GridFSDBFile;

@Service
@Path("/")
public class ImageService {
	@Autowired
	GridFsOperations mongoOperation;

    @GET
    @Path("/{md5}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response find(
    		@PathParam("md5") String md5) {

		Query searchUserQuery = new Query(Criteria.where("md5").is(md5));

		GridFSDBFile file = this.mongoOperation.findOne(searchUserQuery);

        return Response.ok().entity(file.getInputStream()).build();
    }
}

