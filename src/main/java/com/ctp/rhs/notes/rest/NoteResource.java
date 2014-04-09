package com.ctp.rhs.notes.rest;

import com.ctp.rhs.notes.model.Note;
import com.ctp.rhs.notes.service.NoteNotFoundException;
import com.ctp.rhs.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
@RequestMapping("/resource/note")
public class NoteResource
{

   @Autowired
   private NoteService noteService;

   @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
   public @ResponseBody Note find(@PathVariable("id") Long id)
   {
      return noteService.find(id);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delete(@PathVariable("id") Long id)
   {
      noteService.remove(id);
   }

   @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
   @ResponseStatus(HttpStatus.CREATED)
   public void add(@RequestBody Note note)
   {
      noteService.save(note);
   }

   @ExceptionHandler(NoteNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   @ResponseBody
   public String handleNoteNotFoundException(HttpServletRequest req, NoteNotFoundException ex) {
      return ex.getMessage();
   }

}
