package com.redhat.atomic.fruit;

import java.net.URI;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.jboss.logging.Logger;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CloudEventResource {
    Logger logger = Logger.getLogger(CloudEventResource.class);

    @POST
    @Path("/")
    public Response processCloudEvent(
        @HeaderParam("ce-id") String id,
        @HeaderParam("ce-type") String type,
        @HeaderParam("ce-source") String source,
        @HeaderParam("ce-specversion") String specversion,
        @HeaderParam("ce-user") String user,
        @HeaderParam("content-type") String contentType,
        @HeaderParam("content-length") String contentLength,
        Fruit fruit) {
        logger.info("ce-id=" + id);
        logger.info("ce-type=" + type);
        logger.info("ce-source=" + source);
        logger.info("ce-specversion=" + specversion);
    
        logger.info("ce-user=" +user);
        logger.info("content-type=" + contentType);
        logger.info("content-length=" + contentLength);
        
        return saveFruit(fruit);
    }

    @Transactional
    public Response saveFruit(Fruit fruit) {
        // since the FruitEntity is a panache entity
        // persist is available by default
        fruit.persist();
        final URI createdUri = UriBuilder.fromResource(CloudEventResource.class)
                        .path(Long.toString(fruit.id))
                        .build();
        return Response.created(createdUri).build();
    }

}
