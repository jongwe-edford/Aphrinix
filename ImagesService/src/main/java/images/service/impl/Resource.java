package images.service.impl;

import javax.ws.rs.*;

@Path("/hello-world")
public class Resource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}