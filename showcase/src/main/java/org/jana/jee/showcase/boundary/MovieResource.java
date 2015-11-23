/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jana.jee.showcase.boundary;

import java.util.List;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import org.jana.jee.showcase.control.MovieService;
import org.jana.jee.showcase.entity.Message;
import org.jana.jee.showcase.entity.Movie;

/**
 *
 * @author gita
 */
@Path("/movies")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MovieResource {
    
    @Inject
    private MovieService movieService;
    
    /**
     * 
     * @param uriInfo
     * @return 
     */
    @GET
    public Response load(@Context UriInfo uriInfo) {
        List<Movie> movies = movieService.load();
        GenericEntity<List<Movie>> moviePayload=  
                new GenericEntity<List<Movie>>(movies){};
        return Response.ok()
                       .entity(moviePayload)
                       .links(getTransitionalLinks(uriInfo, "id"))
                       .build();
    }
    
    /**
     * 
     * @param id
     * @param uriInfo
     * @return 
     */
    @GET
    @Path("/{id}")
    public Response find(@PathParam("id") @NotNull String id, @Context UriInfo uriInfo) {
        final Movie movie = movieService.find(id);
        if(movie!=null) {
            return Response.status(Status.OK)
                           .entity(movie)
                           .links(getTransitionalLinks(uriInfo, id))
                           .build();
        } else {
            return Response.status(Status.NOT_FOUND)
                           .links(getTransitionalLinks(uriInfo, "id"))
                           .entity(new Message(Message.Code.MOVIE_NOT_FOUND))
                           .build();            
        }
    }
    
    /**
     * 
     * @param movie
     * @param uriInfo
     * @return 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@NotNull Movie movie, @Context UriInfo uriInfo) {
        final Movie existMovie = movieService.find(movie.getIdImdb());
        if(existMovie!=null) {
            return Response.status(Status.CONFLICT)
                           .links(getTransitionalLinks(uriInfo, movie.getIdImdb()))
                           .entity(new Message(Message.Code.MOVIE_EXIST))
                           .build();
        }
        movie = movieService.create(movie);
        if(movie!=null){
            return Response.created(getSelfLink(uriInfo, movie.getIdImdb()).getUri())
                           .links(getTransitionalLinks(uriInfo, movie.getIdImdb()))
                           .entity(new Message(Message.Code.MOVIE_CREATED))
                           .build();
        } else {
            return Response.status(Status.NOT_ACCEPTABLE)
                           .links(getTransitionalLinks(uriInfo, "id"))
                           .entity(new Message(Message.Code.MOVIE_NOT_ACCEPTABLE))
                           .build(); 
        }
    }

    /**
     * 
     * @param movie
     * @param uriInfo
     * @return 
     */    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Movie movie, @Context UriInfo uriInfo) {
        movieService.update(movie);
        return Response.status(Status.OK)
                       .links(getTransitionalLinks(uriInfo, movie.getIdImdb()))
                       .entity(movie)
                       .build();
    }
    
    /**
     * 
     * @param id
     * @param uriInfo
     * @return 
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@NotNull @PathParam("id") String id, @Context UriInfo uriInfo) {
        final Movie movie = movieService.find(id);
        if(movie==null) {
            return Response.status(Status.NOT_FOUND)
                           .links(getTransitionalLinks(uriInfo, "id"))
                           .entity(new Message(Message.Code.MOVIE_NOT_FOUND))
                           .build();
        }
        int result = movieService.delete(movie);
        if(result == 0) {
            return Response.status(Status.ACCEPTED)
                           .links(getTransitionalLinks(uriInfo, movie.getIdImdb()))
                           .entity(movie)
                           .build();
        } else {
            return Response.status(Status.NOT_ACCEPTABLE)
                           .links(getTransitionalLinks(uriInfo, movie.getIdImdb()))       
                           .entity(movie)
                           .build();
        }
    }
    /**
     * 
     * @param uriInfo
     * @param id
     * @return 
     */
    private Link[] getTransitionalLinks(UriInfo uriInfo, String id) {
        Link create = Link.fromUri(uriInfo.getBaseUriBuilder().path(MovieResource.class).build()).rel("create").type(HttpMethod.POST).build();
        Link load = Link.fromUri(uriInfo.getBaseUriBuilder().path(MovieResource.class).build()).rel("load").type(HttpMethod.GET).build();
        Link find = Link.fromUri(uriInfo.getBaseUriBuilder().path(MovieResource.class).path(id).build(id)).rel("find").type(HttpMethod.GET).build();
        Link update = Link.fromUri(uriInfo.getBaseUriBuilder().path(MovieResource.class).path(id).build(id)).rel("update").type(HttpMethod.PUT).build();
        return new Link[]{create, load, find, update};
    }
    
    /**
     * 
     * @param uriInfo
     * @param id
     * @return 
     */
    private Link getSelfLink(UriInfo uriInfo, String id) {
        return Link.fromUri(uriInfo.getBaseUriBuilder().path(MovieResource.class).path(id).build(id)).rel("self").type(HttpMethod.GET).build();
    }
}   
