package com.OnlineLibrary.System.entity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.OnlineLibrary.System.Entity.Author;
import com.OnlineLibrary.System.Entity.Book;
import com.OnlineLibrary.System.Entity.Publisher;

public class BookTest {

 private Book book;
 private Author author;
 private Publisher publisher;

 @BeforeEach
 public void setUp() {
     author = new Author(); // Assume you have a default constructor
     publisher = new Publisher(); // Assume you have a default constructor
     book = new Book("Sample Title", author, publisher);
 }

 @Test
 public void testGettersAndSetters() {
      
     book.setTitle("New Title");
     assertEquals("New Title", book.getTitle());

     // Test author
     Author newAuthor = new Author();
     book.setAuthor(newAuthor);
     assertEquals(newAuthor, book.getAuthor());

     
     Publisher newPublisher = new Publisher();
     book.setPublisher(newPublisher);
     assertEquals(newPublisher, book.getPublisher());

     
     book.setPrice(29.99);
     assertEquals(29.99, book.getPrice());

     
     book.setPageCount(350);
     assertEquals(350, book.getPageCount());

      
     book.setLanguage("English");
     assertEquals("English", book.getLanguage());

     
     book.setRating(4.5);
     assertEquals(4.5, book.getRating());

      
     book.setGenre("Fiction");
     assertEquals("Fiction", book.getGenre());
 }
}

