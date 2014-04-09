package com.ctp.rhs.notes.rest;

import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;

public class Deployments
{

   public static File[] applicationLibraries()
   {
      return Maven.resolver().loadPomFromFile("pom.xml")
            .resolve("com.google.code.gson:gson")
            .withTransitivity()
            .asFile();
   }

   public static File[] testLibraries()
   {
      return Maven.resolver().loadPomFromFile("pom.xml")
                  .resolve("org.assertj:assertj-core")
                  .withTransitivity()
                  .asFile();
   }

   public static GenericArchive webAppFolder()
   {
      return ShrinkWrap.create(GenericArchive.class)
                       .as(ExplodedImporter.class)
                       .importDirectory("src/main/webapp")
                       .as(GenericArchive.class);
   }

}
