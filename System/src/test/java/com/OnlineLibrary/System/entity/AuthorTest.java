package com.OnlineLibrary.System.entity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.OnlineLibrary.System.Entity.Author;
import com.OnlineLibrary.System.Entity.Book;

public class AuthorTest {

 private Author author;

 @BeforeEach
 public void setUp() {
     author = new Author("John", "Doe");
 }

 @Test
 public void testGettersAndSetters() {
      
     author.setFirstName("Jane");
     assertEquals("Jane", author.getFirstName());

      
     author.setLastName("Smith");
     assertEquals("Smith", author.getLastName());

      
     author.setNationality("American");
     assertEquals("American", author.getNationality());

      
     List<Book> books = new ArrayList<>();
     author.setBooks(books);
     assertEquals(books, author.getBooks());
 }

 @Test
 public void testConstructor() {
     Author newAuthor = new Author("Alice", "Johnson");
     assertEquals("Alice", newAuthor.getFirstName());
     assertEquals("Johnson", newAuthor.getLastName());
     assertNull(newAuthor.getNationality());
     assertNull(newAuthor.getBooks());
 }
}

