package com.redhat.atomic.fruit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@Path("/hello")
public class GreetingResource {
    Logger logger = Logger.getLogger(GreetingResource.class); // logging
    
    @ConfigProperty(name = "hello.message")
    String message;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        logger.debug("Hello method is called with message: " + this.message); // logging & custom property
        return message; // custom property
    }
}