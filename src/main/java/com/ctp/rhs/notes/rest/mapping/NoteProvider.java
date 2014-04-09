package com.ctp.rhs.notes.rest.mapping;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.ctp.rhs.notes.model.Note;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class NoteProvider implements MessageBodyWriter<Note>, MessageBodyReader<Note>
{

   private final Gson gson;

   public NoteProvider()
   {
      this.gson = new Gson();
   }

   @Override
   public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
   {
      return type.isAssignableFrom(Note.class);
   }

   @Override
   public long getSize(Note note, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
   {
      return 0;
   }

   @Override
   public void writeTo(Note note, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException
   {
      try (final PrintWriter writer = new PrintWriter(entityStream)) {
         writer.write(gson.toJson(note));
         writer.flush();
      }
   }

   @Override
   public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
   {
      return type.isAssignableFrom(Note.class);
   }

   @Override
   public Note readFrom(Class<Note> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException
   {
      try (final InputStreamReader dis = new InputStreamReader(entityStream))
      {
         return gson.fromJson(dis, type);
      }
   }
}
