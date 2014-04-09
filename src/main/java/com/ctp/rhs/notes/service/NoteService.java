package com.ctp.rhs.notes.service;

import com.ctp.rhs.notes.model.Note;

import java.util.Collection;

public interface NoteService
{
   public Note find(Long id);

   public Note save(Note note);

   public void remove(Note note);

   public void remove(Long id);

   public Collection<Note> all();
}
