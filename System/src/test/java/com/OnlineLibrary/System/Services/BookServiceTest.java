package com.OnlineLibrary.System.Services;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.OnlineLibrary.System.Entity.Author;
import com.OnlineLibrary.System.Entity.Book;
import com.OnlineLibrary.System.Entity.Publisher;
import com.OnlineLibrary.System.Exception. BookNotFoundException;
import com.OnlineLibrary.System.Repository.BookRepository;
import com.OnlineLibrary.System.Service.BookService;

class BookServiceTest {

 @Mock
 private BookRepository bookRepository;

 @InjectMocks
 private BookService bookService;

 @BeforeEach
 void setUp() {
     MockitoAnnotations.openMocks(this);
 }

 @Test
 void testSaveBook() {
     Book book = new Book();
     bookService.saveBook(book);
     verify(bookRepository, times(1)).save(book);
 }

 @Test
 void testGetAllBooks() {
     List<Book> books = Arrays.asList(new Book(), new Book());
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.getAllBooks();
     assertEquals(2, result.size());
 }

 @Test
 void testGetBookById() {
     Book book = new Book();
     when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

     Book result = bookService.getBookById(1L);
     assertNotNull(result);
 }

 @Test
 void testGetBookByIdNotFound() {
     when(bookRepository.findById(1L)).thenReturn(Optional.empty());

     assertThrows(BookNotFoundException.class, () -> {
         bookService.getBookById(1L);
     });
 }


 @Test
 void testGetBooksByAuthor() {
     List<Book> books = Arrays.asList(new Book(), new Book());
     when(bookRepository.findByAuthorFirstName("John")).thenReturn(books);

     List<Book> result = bookService.getBooksByAuthor("John");
     assertEquals(2, result.size());
 }
 
 @Test
 void testGroupBooksByAuthor() {
     Author author1 = new Author();
     author1.setFirstName("John");
     author1.setLastName("Doe");

     Author author2 = new Author();
     author2.setFirstName("Jane");
     author2.setLastName("Smith");

     Book book1 = new Book();
     book1.setAuthor(author1);

     Book book2 = new Book();
     book2.setAuthor(author1);

     Book book3 = new Book();
     book3.setAuthor(author2);

     List<Book> books = Arrays.asList(book1, book2, book3);
     when(bookRepository.findAll()).thenReturn(books);

     Map<String, Long> result = bookService. getBookCountByAuthor();

     assertEquals(2, result.size());
     assertEquals(2L, result.get("John Doe"));
     assertEquals(1L, result.get("Jane Smith"));
 }

 @Test
 void testDeleteBook() {
     bookService.delectBook(1L);
     verify(bookRepository, times(1)).deleteById(1L);
 }

 @Test
 void testSearchBooks() {
     Book book1 = new Book();
     book1.setTitle("Title1");
     Book book2 = new Book();
     book2.setTitle("Title2");

     List<Book> books = Arrays.asList(book1, book2);
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.searchBooks("Title1", null, null);
     assertEquals(1, result.size());
 }

 @Test
 void testSortBooksByTitle() {
     Book book1 = new Book();
     book1.setTitle("B");
     Book book2 = new Book();
     book2.setTitle("A");

     List<Book> books = Arrays.asList(book1, book2);
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.sortBooksByTitle();
     assertEquals("A", result.get(0).getTitle());
 }
 
 @Test
 void testSearchBooks_AllParametersNull() {
     List<Book> books = Arrays.asList(
         new Book("Title1", new Author("John", "Doe"), new Publisher("Publisher1")),
         new Book("Title2", new Author("Jane", "Smith"), new Publisher("Publisher2"))
     );
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.searchBooks(null, null, null);

     assertEquals(2, result.size());
 }

 @Test
 void testSearchBooks_TitleMatch() {
     List<Book> books = Arrays.asList(
         new Book("Title1", new Author("John", "Doe"), new Publisher("Publisher1")),
         new Book("Title2", new Author("Jane", "Smith"), new Publisher("Publisher2"))
     );
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.searchBooks("Title1", null, null);

     assertEquals(1, result.size());
     assertEquals("Title1", result.get(0).getTitle());
 }

 @Test
 void testSearchBooks_AuthorMatch() {
     List<Book> books = Arrays.asList(
         new Book("Title1", new Author("John", "Doe"), new Publisher("Publisher1")),
         new Book("Title2", new Author("Jane", "Smith"), new Publisher("Publisher2"))
     );
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.searchBooks(null, "John", null);

     assertEquals(1, result.size());
     assertEquals("John", result.get(0).getAuthor().getFirstName());
 }

 @Test
 void testSearchBooks_PublisherMatch() {
     List<Book> books = Arrays.asList(
         new Book("Title1", new Author("John", "Doe"), new Publisher("Publisher1")),
         new Book("Title2", new Author("Jane", "Smith"), new Publisher("Publisher2"))
     );
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.searchBooks(null, null, "Publisher1");

     assertEquals(1, result.size());
     assertEquals("Publisher1", result.get(0).getPublisher().getName());
 }

 @Test
 void testSearchBooks_NoMatch() {
     List<Book> books = Arrays.asList(
         new Book("Title1", new Author("John", "Doe"), new Publisher("Publisher1")),
         new Book("Title2", new Author("Jane", "Smith"), new Publisher("Publisher2"))
     );
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.searchBooks("NonExistentTitle", "NonExistentAuthor", "NonExistentPublisher");

     assertEquals(0, result.size());
 }

 @Test
 void testFindBook() {
     Book book = new Book();
     when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

     Optional<Book> result = bookService.findBook(1L);
     assertTrue(result.isPresent());
 }
 
 @Test
 void testSearchBooks_AuthorNull() {
     List<Book> books = Arrays.asList(
         new Book("Title1", null, new Publisher("Publisher1")),
         new Book("Title2", new Author("Jane", "Smith"), new Publisher("Publisher2"))
     );
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.searchBooks(null, null, "Publisher1");

     assertEquals(1, result.size());
     assertNull(result.get(0).getAuthor());
 }
 
 @Test
 void testSearchBooks_PublisherNull() {
     List<Book> books = Arrays.asList(
         new Book("Title1", new Author("John", "Doe"), null),
         new Book("Title2", new Author("Jane", "Smith"), new Publisher("Publisher2"))
     );
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.searchBooks(null, "John", null);

     assertEquals(1, result.size());
     assertNull(result.get(0).getPublisher());
 }
 
 @Test
 void testSearchBooks_MultipleMatches() {
     List<Book> books = Arrays.asList(
         new Book("Title1", new Author("John", "Doe"), new Publisher("Publisher1")),
         new Book("Title1", new Author("John", "Doe"), new Publisher("Publisher2")),
         new Book("Title2", new Author("Jane", "Smith"), new Publisher("Publisher1"))
     );
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.searchBooks("Title1", "John", null);

     assertEquals(2, result.size());
 }

 @Test
 void testSearchBooks_AuthorAndPublisherMatch() {
     List<Book> books = Arrays.asList(
         new Book("Title1", new Author("John", "Doe"), new Publisher("Publisher1")),
         new Book("Title2", new Author("John", "Doe"), new Publisher("Publisher2")),
         new Book("Title3", new Author("Jane", "Smith"), new Publisher("Publisher1"))
     );
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.searchBooks(null, "John", "Publisher1");

     assertEquals(1, result.size());
     assertEquals("Title1", result.get(0).getTitle());
 }
 
// @Test
// void testSearchBooks_AuthorParamNull() {
//     List<Book> books = Arrays.asList(
//         new Book("Title1", new Author("John", "Doe"), new Publisher("Publisher1")),
//         new Book("Title2", new Author("Jane", "Smith"), new Publisher("Publisher2"))
//     );
//     when(bookRepository.findAll()).thenReturn(books);
//
//     List<Book> result = bookService.searchBooks(null, null, null);
//
//     assertEquals(2, result.size());
// }

 @Test
 void testSearchBooks_BookAuthorNull() {
     List<Book> books = Arrays.asList(
         new Book("Title1", null, new Publisher("Publisher1")),
         new Book("Title2", new Author("Jane", "Smith"), new Publisher("Publisher2"))
     );
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.searchBooks(null, "John", null);

     assertEquals(0, result.size());
 }

 @Test
 void testSearchBooks_AuthorNoMatch() {
     List<Book> books = Arrays.asList(
         new Book("Title1", new Author("John", "Doe"), new Publisher("Publisher1")),
         new Book("Title2", new Author("Jane", "Smith"), new Publisher("Publisher2"))
     );
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.searchBooks(null, "NonExistentAuthor", null);

     assertEquals(0, result.size());
 }
 
// @Test
// void testSearchBooks_PublisherParamNull() {
//     List<Book> books = Arrays.asList(
//         new Book("Title1", new Author("John", "Doe"), new Publisher("Publisher1")),
//         new Book("Title2", new Author("Jane", "Smith"), new Publisher("Publisher2"))
//     );
//     when(bookRepository.findAll()).thenReturn(books);
//
//     List<Book> result = bookService.searchBooks(null, null, null);
//
//     assertEquals(2, result.size());
// }

 @Test
 void testSearchBooks_BookPublisherNull() {
     List<Book> books = Arrays.asList(
         new Book("Title1", new Author("John", "Doe"), null),
         new Book("Title2", new Author("Jane", "Smith"), new Publisher("Publisher2"))
     );
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.searchBooks(null, null, "Publisher1");

     assertEquals(0, result.size());
 }

  
 @Test
 void testSearchBooks_PublisherNoMatch() {
     List<Book> books = Arrays.asList(
         new Book("Title1", new Author("John", "Doe"), new Publisher("Publisher1")),
         new Book("Title2", new Author("Jane", "Smith"), new Publisher("Publisher2"))
     );
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.searchBooks(null, null, "NonExistentPublisher");

     assertEquals(0, result.size());
 }
 
// @Test
// void testFilterBooks_AuthorNotNull() {
//     List<Book> books = Arrays.asList(
//         new Book("Title1", new Author("John", "Doe"), new Publisher("Publisher1")),
//         new Book("Title2", new Author("Jane", "Smith"), new Publisher("Publisher2"))
//     );
//     when(bookRepository.findAll()).thenReturn(books);
//
//     List<Book> result = bookService.searchBooks(null, null, null);
//
//     assertEquals(2, result.size());
// }

 @Test
 void testFilterBooks_AuthorIsNull() {
     List<Book> books = Arrays.asList(
         new Book("Title1", null, new Publisher("Publisher1")),
         new Book("Title2", new Author("Jane", "Smith"), new Publisher("Publisher2"))
     );
     when(bookRepository.findAll()).thenReturn(books);

     List<Book> result = bookService.searchBooks(null, null, null);

     assertEquals(2, result.size());
     assertEquals("Title1", result.get(0).getTitle());
 }


}

