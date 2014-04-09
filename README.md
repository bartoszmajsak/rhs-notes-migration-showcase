## Simple REST endpoint for taking notes

### What is this?

This tiny little project has been created for [Red Hat Summit presentation](redhat.com/summit/) and consists of a REST resource for adding notes (in uber simplistic form - plain content ;). The goal is to demonstrate differences between Spring 3.x ([tag](../../tree/spring)) and Java EE ([tag](../../tree/javaee)) approaches when implementing REST oriented applications. On top of that it's showcasing how [Arquillian](http://arquillian.org) perfectly fits for both worlds and hence can greatly simplify migration effort through automated verification of selected components. No more *shabang!* deployments and snowball effect.

The Arquillian test itself comes in two flavours:

* Client-side (black-box) tests covering `POST` and `GET` operation (written in brilliant [REST Assured](http://rest-assured.googlecode.com)). In this case Arquillian is used purely as deployment engine and container controller (you can think of it as more powerful brother of [Cargo](http://cargo.codehaus.org/))

* [Arquillian Warp](http://arquillian.org/modules/warp-extension/) grey-box test for `DELETE` operation, where REST resource is still excercised through [REST Assured](http://rest-assured.googlecode.com), but verification of the underlying persistence storage is done on the server side.
