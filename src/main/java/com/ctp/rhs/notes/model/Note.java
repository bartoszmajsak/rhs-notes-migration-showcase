package com.ctp.rhs.notes.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@ToString
public class Note
{

   @Getter @Setter
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Getter @Setter
   @Basic
   private String content;

   protected Note() {} // To satisfy JPA

   public Note(String content)
   {
      this.content = content;
   }


}
