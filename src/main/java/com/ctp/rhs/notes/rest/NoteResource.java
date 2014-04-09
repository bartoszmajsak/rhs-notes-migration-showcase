package com.ctp.rhs.notes.rest;

import com.ctp.rhs.notes.model.Note;
import com.ctp.rhs.notes.service.NoteNotFoundException;
import com.ctp.rhs.notes.service.NoteService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("note")
public class NoteResource
{

   @Inject
   private NoteService noteService;

   @GET
   @Path("{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public Note find(@PathParam("id") Long id)
   {
      return noteService.find(id);
   }


   @DELETE
   @Path("{id}")
   public Response delete(@PathParam("id") Long id)
   {
      noteService.remove(id);
      return Response.status(Response.Status.NO_CONTENT).build();
   }

   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   public Response add(Note note)
   {
      noteService.save(note);
      return Response.status(Response.Status.CREATED).build();
   }

}
