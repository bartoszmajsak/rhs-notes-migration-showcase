package com.ctp.rhs.notes.rest;

import com.ctp.rhs.notes.model.Note;
import com.ctp.rhs.notes.service.NoteService;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.warp.Activity;
import org.jboss.arquillian.warp.Inspection;
import org.jboss.arquillian.warp.Warp;
import org.jboss.arquillian.warp.WarpTest;
import org.jboss.arquillian.warp.servlet.AfterServlet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.net.URL;

import static com.ctp.rhs.notes.rest.Deployments.*;
import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Arquillian.class)
@WarpTest @RunAsClient
public class NoteResourceTest
{

   @Deployment
   public static Archive<?> createDeployment()
   {
      final WebArchive restEndpointArchive = ShrinkWrap.create(WebArchive.class, "notes.war")
                                                       .addPackages(true, Note.class.getPackage())
                                                       .addPackages(true, NoteService.class.getPackage())
                                                       .addPackages(true, NoteResource.class.getPackage())
                                                       .addAsLibraries(applicationLibraries())
                                                       .addAsLibraries(testLibraries())
                                                       .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                                                       .merge(webAppFolder(), "/", Filters.includeAll());
      return restEndpointArchive;
   }

   @ArquillianResource
   private URL applicationPath;

   @Before
   public void resourcePath()
   {
      RestAssured.baseURI = applicationPath.toString();
      RestAssured.basePath = "/resource";
   }

   @Test @InSequence(1)
   public void should_create_note() throws Exception
   {
      given().
               contentType(ContentType.JSON).
               body("{\"content\" : \"Arquillian rocks!\"}").
     when().
               post("/note").
     then().
            assertThat().
               statusCode(equalTo(201));
   }

   @Test @InSequence(2)
   public void should_display_note() throws Exception
   {
      given().
              pathParameter("id", 1).
      when().
              get("/note/{id}").
      then().
              assertThat().
                  statusCode(equalTo(200)).
                  body("id", equalTo(1)).
                  body("content", containsString("Arquillian rocks!"));
   }

   @Test @InSequence(3)
   public void should_delete_note() throws Exception
   {
      Warp.initiate(new Activity()
      {
         @Override
         public void perform()
         {
            given().
                  pathParameter("id", 1).
            when().
                  delete("/note/{id}").
            then().
                  assertThat().statusCode(equalTo(204));
         }
      })
      .inspect(new NoteDataStoreInspection());
   }

   public static class NoteDataStoreInspection extends Inspection
   {
         private static final long serialVersionUID = -778115683463909014L;

         @Inject
         private NoteService noteService;

         @AfterServlet
         public void verifyNoEntriesInDatabase()
         {
            assertThat(noteService.all()).isEmpty();
         }
   }

}
