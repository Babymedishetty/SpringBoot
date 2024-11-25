package com.OnlineLibrary.System.Controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import com.OnlineLibrary.System.Dto.ResponseDto;
import com.OnlineLibrary.System.Entity.Author;
import com.OnlineLibrary.System.Entity.Book;
import com.OnlineLibrary.System.Entity.Publisher;
import com.OnlineLibrary.System.Exception.BookNotFoundException;

import com.OnlineLibrary.System.Repository.AuthorRepository;
import com.OnlineLibrary.System.Repository.PublisherRepository;
import com.OnlineLibrary.System.Service.AuthorService;
import com.OnlineLibrary.System.Service.BookService;
import com.OnlineLibrary.System.Service.PublisherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private PublisherService publisherService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private PublisherRepository publisherRepository;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private ResponseDto responseDto;

    @Test
    void testAuthorNotFound() throws Exception {
        Book book = new Book();

        Author author = new Author();
        author.setAuthorId(2L);
        book.setAuthor(author);

        Publisher publisher = new Publisher();
        publisher.setPublisherId(1L);
        book.setPublisher(publisher);

        book.setBookId(1L);
        book.setTitle("Sample Book");
        book.setGenre("Fiction");
        book.setPrice(300.0);
        book.setLanguage("English");
        book.setPageCount(300);
        book.setRating(4.5);
        book.setAuthor(author);
        book.setPublisher(publisher);

        when(authorRepository.existsById(2L)).thenReturn(false);

        mockMvc.perform(post("/api/book/insert")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Author with ID 2 not present in the system. Please enter a valid ID."));
    }

    @Test
    void testPublisherNotFound() throws Exception {
        Book book = new Book();

        Author author = new Author();
        author.setAuthorId(2L);
        book.setAuthor(author);

        Publisher publisher = new Publisher();
        publisher.setPublisherId(1L);
        book.setPublisher(publisher);

        book.setBookId(1L);
        book.setTitle("Sample Book");
        book.setGenre("Fiction");
        book.setPrice(300.0);
        book.setLanguage("English");
        book.setPageCount(300);
        book.setRating(4.5);
        book.setAuthor(author);
        book.setPublisher(publisher);

        when(authorRepository.existsById(2L)).thenReturn(true);
        when(publisherRepository.existsById(1L)).thenReturn(false);

        mockMvc.perform(post("/api/book/insert")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Publisher with ID 1 not present in the system. Please enter a valid ID."));
    }

    @Test
    void testCreateBook() throws Exception {
         
        Book book = new Book();

        
        Author author = new Author();
        author.setAuthorId(2L);
        book.setAuthor(author);

         
        Publisher publisher = new Publisher();
        publisher.setPublisherId(1L);
        book.setPublisher(publisher);

         
        book.setBookId(1L);
        book.setTitle("Sample Book");
        book.setGenre("Fiction");
        book.setPrice(300.0);
        book.setLanguage("English");
        book.setPageCount(300);
        book.setRating(4.5);

         
        when(authorRepository.existsById(2L)).thenReturn(true);
        when(publisherRepository.existsById(1L)).thenReturn(true);

        
        mockMvc.perform(post("/api/book/insert")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(content().string("Book created successfully."));
    }
    
    @Test
    void testCreateBook_EmptyRequestBody() throws Exception {
        mockMvc.perform(post("/api/book/insert")
                .contentType("application/json")
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Author information is missing."));
    }
    
    @Test
    void testCreateBook_UnsupportedContentType() throws Exception {
        mockMvc.perform(post("/api/book/insert")
                .contentType("text/plain") // Unsupported content type
                .content("{\"title\":\"Sample Book\",\"author\":{\"authorId\":1}}"))
                .andExpect(status().isUnsupportedMediaType());
    }
    
    @Test
    void testCreateBook_InvalidJsonFormat() throws Exception {
        mockMvc.perform(post("/api/book/insert")
                .contentType("application/json")
                .content("{title:Sample Book, author:{authorId:1}}")) // Invalid JSON format
                .andExpect(status().isBadRequest());
    }
    

    
    @Test
    void testGetBookById() throws Exception {
        
        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("Sample Book");
        book.setGenre("Fiction");
        book.setPrice(300.0);
        book.setLanguage("English");
        book.setPageCount(300);
        book.setRating(4.5);

        Author author = new Author();
        author.setAuthorId(2L);
        book.setAuthor(author);

        Publisher publisher = new Publisher();
        publisher.setPublisherId(1L);
        book.setPublisher(publisher);

        Mockito.when(bookService.getBookById(anyLong())).thenReturn(book);

        
        mockMvc.perform(get("/api/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(book)));
    }

    @Test
     void testGetBookById_NotFound() throws Exception {
        Mockito.when(bookService.getBookById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/api/book/1"))
                .andExpect(status().isNotFound());
    }
    
    
    
    @Test
     void testUpdateBook_Success() throws Exception {
         
        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("Updated Book");
        book.setGenre("Fiction");
        book.setPrice(350.0);
        book.setLanguage("English");
        book.setPageCount(320);
        book.setRating(4.8);

        Author author = new Author();
        author.setAuthorId(2L);
        book.setAuthor(author);

        Publisher publisher = new Publisher();
        publisher.setPublisherId(1L);
        book.setPublisher(publisher);

        Book bookDB = new Book();
        bookDB.setBookId(1L);
        bookDB.setTitle("Sample Book");
        bookDB.setGenre("Fiction");
        bookDB.setPrice(300.0);
        bookDB.setLanguage("English");
        bookDB.setPageCount(300);
        bookDB.setRating(4.5);
        bookDB.setAuthor(author);
        bookDB.setPublisher(publisher);

        Mockito.when(bookService.findBook(anyLong())).thenReturn(Optional.of(bookDB));
        Mockito.doNothing().when(bookService).saveBook(any(Book.class));
        Mockito.when(responseDto.getMessage()).thenReturn("Book Details Updated");

         
        mockMvc.perform(put("/api/book/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Book Details Updated\"}"));
    }

    @Test
      void testUpdateBook_InvalidId() throws Exception {
        
        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("Updated Book");
        book.setGenre("Fiction");
        book.setPrice(350.0);
        book.setLanguage("English");
        book.setPageCount(320);
        book.setRating(4.8);

        Mockito.when(bookService.findBook(anyLong())).thenReturn(Optional.empty());
        Mockito.when(responseDto.getMessage()).thenReturn("Invalid ID!!, Please enter valid ID");

         
        mockMvc.perform(put("/api/book/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\":\"Invalid ID!!, Please enter valid ID\"}"));
    }
    
    @Test
      void testSearchBooks_WithResults() throws Exception {
    	
    	  Book book1 = new Book();
    	
    	 Author author1 = new Author();
         author1.setAuthorId(2L);
         book1.setAuthor(author1);

         Publisher publisher1 = new Publisher();
         publisher1.setPublisherId(1L);
         book1.setPublisher(publisher1);

        book1.setTitle("The last queen");
        book1.setAuthor(author1);
        book1.setPublisher(publisher1);
        book1.setGenre("Fiction");
        book1.setPrice(300);
        book1.setLanguage("English");
        book1.setPageCount(300);
        book1.setRating(4.5);

        Author author2 = new Author();
        author2.setAuthorId(2L);
        book1.setAuthor(author2);

        Publisher publisher2 = new Publisher();
        publisher2.setPublisherId(1L);
        book1.setPublisher(publisher2);

        Book book2 = new Book();
        book2.setTitle("Title2");
        book2.setAuthor(author2);
        book2.setPublisher(publisher2);
        book2.setGenre("Non-Fiction");
        book2.setPrice(400);
        book2.setLanguage("Spanish");
        book2.setPageCount(250);
        book2.setRating(4.0);

        List<Book> books = Arrays.asList(book1, book2);

        Mockito.when(bookService.searchBooks("The last queen", "Author1", "Publisher1")).thenReturn(books);

        mockMvc.perform(get("/api/book/search")
                .param("title", "The last queen")
                .param("author", "Author1")
                .param("publisher", "Publisher1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("The last queen")))
                .andExpect(jsonPath("$[0].genre", is("Fiction")))
                .andExpect(jsonPath("$[0].price", is(300.0)))
                .andExpect(jsonPath("$[0].language", is("English")))
                .andExpect(jsonPath("$[0].pageCount", is(300)))
                .andExpect(jsonPath("$[0].rating", is(4.5)))
                .andExpect(jsonPath("$[1].title", is("Title2")))
                .andExpect(jsonPath("$[1].genre", is("Non-Fiction")))
                .andExpect(jsonPath("$[1].price", is(400.0)))
                .andExpect(jsonPath("$[1].language", is("Spanish")))
                .andExpect(jsonPath("$[1].pageCount", is(250)))
                .andExpect(jsonPath("$[1].rating", is(4.0)));
    }

    @Test
     void testSearchBooks_NoResults() throws Exception {
        Mockito.when(bookService.searchBooks("NonExistentTitle", "NonExistentAuthor", "NonExistentPublisher"))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/book/search")
                .param("title", "NonExistentTitle")
                .param("author", "NonExistentAuthor")
                .param("publisher", "NonExistentPublisher"))
                .andExpect(status().isNoContent());
    }
    @Test
      void testSearchBooks_WithoutParams() throws Exception {
    	
    	 Book book1 = new Book();
     	
    	 Author author1 = new Author();
         author1.setAuthorId(2L);
         book1.setAuthor(author1);

         Publisher publisher1 = new Publisher();
         publisher1.setPublisherId(1L);
         book1.setPublisher(publisher1);

        book1.setTitle("The last queen");
        book1.setAuthor(author1);
        book1.setPublisher(publisher1);
        book1.setGenre("Fiction");
        book1.setPrice(300);
        book1.setLanguage("English");
        book1.setPageCount(300);
        book1.setRating(4.5);

        Author author2 = new Author();
        author2.setAuthorId(2L);
        book1.setAuthor(author2);

        Publisher publisher2 = new Publisher();
        publisher2.setPublisherId(1L);
        book1.setPublisher(publisher2);

        Book book2 = new Book();
        book2.setTitle("Title2");
        book2.setAuthor(author2);
        book2.setPublisher(publisher2);
        book2.setGenre("Non-Fiction");
        book2.setPrice(400);
        book2.setLanguage("Spanish");
        book2.setPageCount(250);
        book2.setRating(4.0);

        List<Book> books = Arrays.asList(book1, book2);

        Mockito.when(bookService.searchBooks(null, null, null)).thenReturn(books);

        mockMvc.perform(get("/api/book/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("The last queen")))
                .andExpect(jsonPath("$[0].genre", is("Fiction")))
                .andExpect(jsonPath("$[0].price", is(300.0)))
                .andExpect(jsonPath("$[0].language", is("English")))
                .andExpect(jsonPath("$[0].pageCount", is(300)))
                .andExpect(jsonPath("$[0].rating", is(4.5)))
                .andExpect(jsonPath("$[1].title", is("Title2")))
                .andExpect(jsonPath("$[1].genre", is("Non-Fiction")))
                .andExpect(jsonPath("$[1].price", is(400.0)))
                .andExpect(jsonPath("$[1].language", is("Spanish")))
                .andExpect(jsonPath("$[1].pageCount", is(250)))
                .andExpect(jsonPath("$[1].rating", is(4.0)));
    }
    
    
    @Test
      void testGetAllBooks_ReturnsBooks() throws Exception {
    	
    	Book book1 = new Book();
    	
   	 Author author1 = new Author();
        author1.setAuthorId(2L);
        book1.setAuthor(author1);

        Publisher publisher1 = new Publisher();
        publisher1.setPublisherId(1L);
        book1.setPublisher(publisher1);

       book1.setTitle("The Last Queen");
       book1.setAuthor(author1);
       book1.setPublisher(publisher1);
       book1.setGenre("Fiction");
       book1.setPrice(300);
       book1.setLanguage("English");
       book1.setPageCount(300);
       book1.setRating(4.5);

       Author author2 = new Author();
       author2.setAuthorId(2L);
       book1.setAuthor(author2);

       Publisher publisher2 = new Publisher();
       publisher2.setPublisherId(1L);
       book1.setPublisher(publisher2);

       Book book2 = new Book();
       book2.setTitle("Title2");
       book2.setAuthor(author2);
       book2.setPublisher(publisher2);
       book2.setGenre("Non-Fiction");
       book2.setPrice(400);
       book2.setLanguage("Spanish");
       book2.setPageCount(250);
       book2.setRating(4.0);
       
       List<Book> bookList = Arrays.asList(book1, book2);
        
        Mockito.when(bookService.getAllBooks()).thenReturn(bookList);

        
        mockMvc.perform(get("/api/book/getAllBooks"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].title", is("The Last Queen")))
        .andExpect(jsonPath("$[0].genre", is("Fiction")))
        .andExpect(jsonPath("$[0].price", is(300.0)))
        .andExpect(jsonPath("$[0].language", is("English")))
        .andExpect(jsonPath("$[0].pageCount", is(300)))
        .andExpect(jsonPath("$[0].rating", is(4.5)))
        .andExpect(jsonPath("$[1].title", is("Title2")))
        .andExpect(jsonPath("$[1].genre", is("Non-Fiction")))
        .andExpect(jsonPath("$[1].price", is(400.0)))
        .andExpect(jsonPath("$[1].language", is("Spanish")))
        .andExpect(jsonPath("$[1].pageCount", is(250)))
        .andExpect(jsonPath("$[1].rating", is(4.0)));
    }
    
    @Test
      void testGetAllBooks_ReturnsNoBooks() throws Exception {
    	
    	Mockito.when(bookService.getAllBooks()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/book/getAllBooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));  
    }
    
    @Test
      void testDeleteBook_Success() throws Exception {
        Long bookId = 1L;
        doNothing().when(bookService).delectBook(bookId);

        mockMvc.perform(delete("/api/book/delete/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Book deleted Successfully"));
    }

    @Test
      void testDeleteBook_NotFound() throws Exception {
        Long bookId = 1L;
        doThrow(new BookNotFoundException("Book not found")).when(bookService).delectBook(bookId);

        mockMvc.perform(delete("/api/book/delete/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found"));
    }
    
    @Test
     void sortBooksByTitle_Success() throws Exception {
        Book book1 = new Book();
        Author author1 = new Author();
        author1.setAuthorId(2L);
        book1.setAuthor(author1);

        Publisher publisher1 = new Publisher();
        publisher1.setPublisherId(1L);
        book1.setPublisher(publisher1);

        book1.setTitle("The Last Queen");
        book1.setAuthor(author1);
        book1.setPublisher(publisher1);
        book1.setGenre("Fiction");
        book1.setPrice(300);
        book1.setLanguage("English");
        book1.setPageCount(300);
        book1.setRating(4.5);

        Book book2 = new Book();
        Author author2 = new Author();
        author2.setAuthorId(2L);
        book2.setAuthor(author2);

        Publisher publisher2 = new Publisher();
        publisher2.setPublisherId(1L);
        book2.setPublisher(publisher2);

        book2.setTitle("A Tale of Two Cities");
        book2.setAuthor(author2);
        book2.setPublisher(publisher2);
        book2.setGenre("Non-Fiction");
        book2.setPrice(400);
        book2.setLanguage("Spanish");
        book2.setPageCount(250);
        book2.setRating(4.0);

        List<Book> sortedBooks = Arrays.asList(book2, book1);

        Mockito.when(bookService.sortBooksByTitle()).thenReturn(sortedBooks);

        mockMvc.perform(get("/api/book/sort"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].title", is("A Tale of Two Cities")))
            .andExpect(jsonPath("$[0].genre", is("Non-Fiction")))
            .andExpect(jsonPath("$[0].price", is(400.0)))
            .andExpect(jsonPath("$[0].language", is("Spanish")))
            .andExpect(jsonPath("$[0].pageCount", is(250)))
            .andExpect(jsonPath("$[0].rating", is(4.0)))
            .andExpect(jsonPath("$[1].title", is("The Last Queen")))
            .andExpect(jsonPath("$[1].genre", is("Fiction")))
            .andExpect(jsonPath("$[1].price", is(300.0)))
            .andExpect(jsonPath("$[1].language", is("English")))
            .andExpect(jsonPath("$[1].pageCount", is(300)))
            .andExpect(jsonPath("$[1].rating", is(4.5)));
    }

    @Test
      void sortBooksByTitle_NoContent() throws Exception {
        List<Book> sortedBooks = new ArrayList<>();

        Mockito.when(bookService.sortBooksByTitle()).thenReturn(sortedBooks);

        mockMvc.perform(get("/api/book/sort"))
            .andExpect(status().isNoContent());
    }
    @Test
      void getReport_Success() throws Exception {
        Map<String, Long> report = new HashMap<>();
        report.put("Fiction", 10L);
        report.put("Non-Fiction", 5L);

        Mockito.when(bookService.getBookCountByAuthor()).thenReturn(report);

        mockMvc.perform(get("/api/book/report"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.Fiction", is(10)))
            .andExpect(jsonPath("$.Non-Fiction", is(5)));
    }

    @Test
      void getReport_NoContent() throws Exception {
        Map<String, Long> report = new HashMap<>();

        Mockito.when(bookService.getBookCountByAuthor()).thenReturn(report);

        mockMvc.perform(get("/api/book/report"))
            .andExpect(status().isNoContent());
    }
    }
