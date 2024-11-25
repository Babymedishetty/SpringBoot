package com.OnlineLibrary.System.entity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.OnlineLibrary.System.Entity.Book;
import com.OnlineLibrary.System.Entity.Publisher;

public class PublisherTest {

 private Publisher publisher;

 @BeforeEach
 public void setUp() {
     publisher = new Publisher("Penguin Books");
 }

 @Test
 public void testSettersAndGetters() {
     
     publisher.setName("HarperCollins");
     assertEquals("HarperCollins", publisher.getName());

     
     publisher.setAddress("123 Main St");
     assertEquals("123 Main St", publisher.getAddress());

      
     publisher.setContactNumber("123-456-7890");
     assertEquals("123-456-7890", publisher.getContactNumber());

     // Test books
     List<Book> books = new ArrayList<>();
     publisher.setBooks(books);
     assertEquals(books, publisher.getBooks());
 }

 @Test
 public void testConstructor() {
     Publisher newPublisher = new Publisher("Random House");
     assertEquals("Random House", newPublisher.getName());
     assertNull(newPublisher.getAddress());
     assertNull(newPublisher.getContactNumber());
     assertNull(newPublisher.getBooks());
 }
}

