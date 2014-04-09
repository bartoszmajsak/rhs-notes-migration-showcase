package com.ctp.rhs.notes.service;

import com.ctp.rhs.notes.model.Note;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class JpaNoteService implements NoteService
{

   @PersistenceContext
   private EntityManager entityManager;

   public Note find(Long id)
   {
      final Note note = entityManager.find(Note.class, id);
      if (note == null)
      {
         throw new NoteNotFoundException("Unable to find note using id {" + id + "}");
      }
      return note;
   }

   @Override
   public Note save(Note note)
   {
      if (note.getId() == null)
      {
         entityManager.persist(note);
      }
      else
      {
         note = entityManager.merge(note);
      }
      return note;
   }

   @Override
   public void remove(Note note)
   {
      entityManager.remove(note);
   }

   @Override
   public void remove(Long id)
   {
      remove(entityManager.find(Note.class, id));
   }

   @Override
   public Collection<Note> all()
   {
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      CriteriaQuery<Note> query = criteriaBuilder.createQuery(Note.class);
      Root<Note> from = query.from(Note.class);
      CriteriaQuery<Note> select = query.select(from);

      final Set<Note> result = new HashSet<Note>();
      result.addAll(entityManager.createQuery(select).getResultList());

      return result;
   }
}
