package com.ctp.rhs.notes.rest.mapping;

import com.ctp.rhs.notes.service.NoteNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoteNotFoundExceptionMapper implements ExceptionMapper<NoteNotFoundException>
{
   @Override
   public Response toResponse(NoteNotFoundException exception)
   {
      return Response.status(Response.Status.NOT_FOUND)
                     .type(MediaType.APPLICATION_JSON)
                     .entity(exception.getMessage())
                     .build();
   }

}
