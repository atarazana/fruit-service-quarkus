package com.redhat.atomic.fruit;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.jboss.logging.Logger;

@Path("/fruit")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FruitResource {
    Logger logger = Logger.getLogger(FruitResource.class);
    
    @GET
    public List<Fruit> allFruits() {
        return Fruit.listAll(); 
    }

    @GET
    @Path("{season}")
    public List<Fruit> fruitsBySeason(@PathParam("season") String season) {
        return Fruit.getAllFruitsForSeason(season);
    }

    @POST
    @Transactional
    public Response saveFruit(Fruit fruit) {
        // since the FruitEntity is a panache entity
        // persist is available by default
        fruit.persist();
        final URI createdUri = UriBuilder.fromResource(FruitResource.class)
                        .path(Long.toString(fruit.id))
                        .build();
        return Response.created(createdUri).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateFruit(@PathParam("id") Long id, Fruit fruit) {
        logger.info(String.format("id: %s fruit: %s", id, fruit));

        // since the FruitEntity is a panache entity
        // persist is available by default
        Fruit found = Fruit.findById(id);
        logger.info("found" + found);
        if (found != null) {
            found.name = fruit.name;
            found.season = fruit.season;
            found.persist();
        } else {
            fruit.persist();
        }
        
        final URI createdUri = UriBuilder.fromResource(FruitResource.class)
                        .path(Long.toString(id))
                        .build();
        return Response.created(createdUri).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deleteFruit(@PathParam("id") Long id) {
        // since the FruitEntity is a panache entity
        // persist is available by default
        Fruit.deleteById(id);
    }
}