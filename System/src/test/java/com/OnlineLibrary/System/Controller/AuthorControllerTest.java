package com.OnlineLibrary.System.Controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.OnlineLibrary.System.Entity.Author;
import com.OnlineLibrary.System.Exception.AuthorNotFoundException;
import com.OnlineLibrary.System.Repository.AuthorRepository;
import com.OnlineLibrary.System.Service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;
    
    @MockBean
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorController authorController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Author author;
    
    private List<Author> authors;
    
    @BeforeEach
    public void setUp() {
    	
    	 author = new Author();
         author.setAuthorId(1L);
         author.setFirstName("John");
         author.setLastName("Doe");
         author.setNationality("American");
    	
    	
        Author author1 = new Author();
        author1.setAuthorId(1L);
        author1.setFirstName("John");
        author1.setLastName("Doe");
        author1.setNationality("American");

        Author author2 = new Author();
        author2.setAuthorId(2L);
        author2.setFirstName("Jane");
        author2.setLastName("Smith");
        author2.setNationality("British");

        authors = Arrays.asList(author1, author2);
    }

    

    @Test
    void testCreateAuthor_Success() throws Exception {
    	
        doNothing().when(authorService).saveAuthor(any(Author.class));

        mockMvc.perform(post("/api/author/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isCreated())  
                .andExpect(content().string("Author created successfully"));
    }

    @Test
    void testCreateAuthor_NullAuthor() throws Exception {
        mockMvc.perform(post("/api/author/create")
                .contentType("application/json")
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Author cannot be null or empty"));
    }

    @Test
    void testCreateAuthor_InternalServerError() throws Exception {
    	
        doThrow(new RuntimeException("Database error")).when(authorService).saveAuthor(any(Author.class));

        mockMvc.perform(post("/api/author/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred: Database error"));
    }
    
    @Test
    public void testGetAllAuthorRuntimeException() {
        when(authorService.getAllAuthors()).thenThrow(new RuntimeException("Service error"));
        ResponseEntity<List<Author>> response = authorController.getAllAuthor();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
    
    @Test
    void testGetAllAuthors_Success() throws Exception {
        when(authorService.getAllAuthors()).thenReturn(authors);

        mockMvc.perform(get("/api/author/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].authorId").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].nationality").value("American"))
                .andExpect(jsonPath("$[1].authorId").value(2L))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"))
                .andExpect(jsonPath("$[1].nationality").value("British"));
    }

    @Test
    void testGetAllAuthors_EmptyList() throws Exception {
        when(authorService.getAllAuthors()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/author/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }
    
    
    @Test
    void testGetBookById() throws Exception {
        
        Mockito.when(authorService.getAuthorById(anyLong())).thenReturn(author);
        mockMvc.perform(get("/api/author/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(author)));
    }
    
    @Test
    void testGetBookById_NotFound() throws Exception {
       Mockito.when(authorService.getAuthorById(anyLong())).thenReturn(null);
       mockMvc.perform(get("/api/author/1"))
               .andExpect(status().isNotFound());
   }
    
    @Test
    void testUpdateAuthor_Success() throws Exception {
        Long authorId = 1L;
        when(authorService.findAuthor(authorId)).thenReturn(Optional.of(author));

        mockMvc.perform(put("/api/author/update/{authorId}", authorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(author)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Author Details Updated"));

        verify(authorService, times(1)).saveAuthor(author);
    }

    @Test
    void testUpdateAuthor_InvalidId() throws Exception {
        Long authorId = 1L;
        when(authorService.findAuthor(authorId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/author/update/{authorId}", authorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(author)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Invalid ID!!, Please enter valid author ID"));

        verify(authorService, never()).saveAuthor(any(Author.class));
    }
    
    @Test
    void testDeleteauthor_Success() throws Exception {
      Long authorId = 1L;
      doNothing().when(authorService).delectAuthor(authorId);

      mockMvc.perform(delete("/api/author/delete/{id}", authorId)
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content().string("author deleted Successfully"));
  }

  @Test
    void testDeleteAuthor_NotFound() throws Exception {
      Long authorId = 1L;
      doThrow(new AuthorNotFoundException("Author not found")).when(authorService).delectAuthor(authorId);

      mockMvc.perform(delete("/api/author/delete/{id}", authorId)
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNotFound())
              .andExpect(content().string("Author not found"));
  }
  

}
