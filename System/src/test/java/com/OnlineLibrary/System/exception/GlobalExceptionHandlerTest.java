package com.OnlineLibrary.System.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.mockito.Mockito.when;
import com.OnlineLibrary.System.Exception.AuthorNotFoundException;
import com.OnlineLibrary.System.Exception.BookNotFoundException;
import com.OnlineLibrary.System.Exception.PublisherNotFoundException;
import com.OnlineLibrary.System.Repository.AuthorRepository;
import com.OnlineLibrary.System.Repository.BookRepository;
import com.OnlineLibrary.System.Repository.PublisherRepository;
import com.OnlineLibrary.System.Service.AuthorService;
import com.OnlineLibrary.System.Service.BookService;
import com.OnlineLibrary.System.Service.PublisherService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
@WebMvcTest
class GlobalExceptionHandlerTest {
 
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private PublisherService publisherService;
    
    @MockBean
    private BookRepository bookRepository;
    
    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private PublisherRepository publisherRepository;


 
    @Autowired
    private WebApplicationContext webApplicationContext;
 
    @BeforeEach
    public void setup() {
        mockMvc = webApplicationContext.getBean(MockMvc.class);
    }
 
    @Test
    void testHandleAuthorNotFoundExceptionTest5() throws Exception {
       
        when(authorService.getAuthorById(1L)).thenThrow(new AuthorNotFoundException("Author not found"));
        mockMvc.perform(get("/api/author/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Author not found"));
    }
    @Test
    void testHandleBookNotFoundExceptionTest5() throws Exception {
       
        when(bookService.getBookById(1L)).thenThrow(new BookNotFoundException("Book not found"));
        mockMvc.perform(get("/api/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found"));
    }
    
    @Test
    void testHandlePublisherNotFoundExceptionTest5() throws Exception {
       
        when(publisherService.getPublisherById(1L)).thenThrow(new PublisherNotFoundException("Publisher not found"));
        mockMvc.perform(get("/api/publisher/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Publisher not found"));
    }
    
    
}