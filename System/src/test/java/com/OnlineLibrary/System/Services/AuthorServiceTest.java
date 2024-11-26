package com.OnlineLibrary.System.Services;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.OnlineLibrary.System.Entity.Author;
import com.OnlineLibrary.System.Exception. AuthorNotFoundException;
import com.OnlineLibrary.System.Repository.AuthorRepository;
import com.OnlineLibrary.System.Service.AuthorService;

class AuthorServiceTest {

 @Mock
 private AuthorRepository authorRepository;

 @InjectMocks
 private AuthorService authorService;

 @BeforeEach
 void setUp() {
     MockitoAnnotations.openMocks(this);
 }

 @Test
 void testSaveAuthor() {
     Author author = new Author();
     authorService.saveAuthor(author);
     verify(authorRepository, times(1)).save(author);
 }

 @Test
 void testGetAllAuthors() {
     List<Author> authors = Arrays.asList(new Author(), new Author());
     when(authorRepository.findAll()).thenReturn(authors);

     List<Author> result = authorService.getAllAuthors();
     assertEquals(2, result.size());
 }

 @Test
 void testGetAllAuthorsThrowsExceptionWhenEmpty() {
     when(authorRepository.findAll()).thenReturn(Arrays.asList());

     assertThrows( AuthorNotFoundException.class, () -> {
         authorService.getAllAuthors();
     });
 }

 @Test
 void testGetAuthorById() {
     Author author = new Author();
     when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

     Author result = authorService.getAuthorById(1L);
     assertNotNull(result);
 }

 @Test
 void testGetAuthorByIdThrowsExceptionWhenNotFound() {
     when(authorRepository.findById(1L)).thenReturn(Optional.empty());

     assertThrows (AuthorNotFoundException.class, () -> {
         authorService.getAuthorById(1L);
     });
 }

 @Test
 void testDeleteAuthor() {
     authorService.delectAuthor(1L);
     verify(authorRepository, times(1)).deleteById(1L);
 }

 @Test
 void testFindAuthor() {
     Author author = new Author();
     when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

     Optional<Author> result = authorService.findAuthor(1L);
     assertTrue(result.isPresent());
 }
}

