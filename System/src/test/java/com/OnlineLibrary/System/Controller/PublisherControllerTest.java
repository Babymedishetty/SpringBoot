package com.OnlineLibrary.System.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

import com.OnlineLibrary.System.Dto.ResponseDto;
import com.OnlineLibrary.System.Entity.Author;
import com.OnlineLibrary.System.Entity.Publisher;
import com.OnlineLibrary.System.Exception.PublisherNotFoundException;
import com.OnlineLibrary.System.Repository.PublisherRepository;
import com.OnlineLibrary.System.Service.PublisherService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PublisherController.class)
 class PublisherControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
    private PublisherService publisherService;
    
    @MockBean
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherController publisherController;

    private ObjectMapper objectMapper = new ObjectMapper();
    
    private Publisher publisher;
    
    private List<Publisher> publishers;

     
    
    @MockBean
    private ResponseDto responseDto;
    
    @BeforeEach
    public void setUp() {
        publisher = new Publisher();
        publisher.setPublisherId(1L);
        publisher.setName("Test Publisher");
        publisher.setAddress("Hyderabad");
        publisher.setContactNumber("1234567893");
        
     Publisher publisher1 = new Publisher(); 
     publisher1.setPublisherId(1L);
     publisher1.setName("Test Publisher");
     publisher1.setAddress("Hyderabad");
     publisher1.setContactNumber("1234567893");

     Publisher publisher2 = new Publisher(); 
     publisher2.setPublisherId(2L);
     publisher2.setName("Test Publisher");
     publisher2.setAddress("Chennai");
     publisher2.setContactNumber("9876543210");


     publishers = Arrays.asList(publisher1, publisher2);
    }
    @Test
    void testCreatePublisher_Success() throws Exception {
        
    	doNothing().when(publisherService).savePublisher(any(Publisher.class));


        mockMvc.perform(post("/api/publisher/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(publisher)))
                .andExpect(status().isCreated())
               .andExpect(content().string("Publisher created successfully"));
    }
    @Test
    void testCreatePublisher_NullPublisher() throws Exception {
        mockMvc.perform(post("/api/publisher/create")
                .contentType("application/json")
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Publisher cannot be null or empty"));
    }

    @Test
    void testCreatePublisher_InternalServerError() throws Exception {
    	
        doThrow(new RuntimeException("Database error")).when(publisherService).savePublisher(any(Publisher.class));

        mockMvc.perform(post("/api/publisher/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(publisher)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred: Database error"));
    }
    
    
    @Test
    void testGetAllPublisher_Success() throws Exception {
        when(publisherService.getAllPublisher()).thenReturn(publishers);

        mockMvc.perform(get("/api/publisher/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].publisherId").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test Publisher"))
                .andExpect(jsonPath("$[0].address").value("Hyderabad"))
                .andExpect(jsonPath("$[0].contactNumber").value("1234567893"))
                .andExpect(jsonPath("$[1].publisherId").value(2L))
                .andExpect(jsonPath("$[1].name").value("Test Publisher"))
                .andExpect(jsonPath("$[1].address").value("Chennai"))
                .andExpect(jsonPath("$[1].contactNumber").value("9876543210"));
    }

    @Test
    void testGetAllPublisher_EmptyList() throws Exception {
        when(publisherService.getAllPublisher()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/publisher/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }
    
    @Test
     void testGetAllPublisherRuntimeException() {
        when(publisherService.getAllPublisher()).thenThrow(new RuntimeException("Service error"));
        ResponseEntity<List<Publisher>> response = publisherController.getAllPublisher();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
    
    
    @Test
    void testGetBookById() throws Exception {
        
        Mockito.when(publisherService.getPublisherById(anyLong())).thenReturn(publisher);
        mockMvc.perform(get("/api/publisher/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(publisher)));
    }
    
    @Test
    void testGetBookById_NotFound() throws Exception {
       Mockito.when(publisherService.getPublisherById(anyLong())).thenReturn(null);
       mockMvc.perform(get("/api/publisher/1"))
               .andExpect(status().isNotFound());
   }
    
    @Test
    void testUpdatePublisher_Success() throws Exception {
        Long publisherId = 1L;
        when(publisherService.findPublisher(publisherId)).thenReturn(Optional.of(publisher));

        mockMvc.perform(put("/api/publisher/update/{publisherId}", publisherId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(publisher)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Publisher Details Updated"));

        verify(publisherService, times(1)).savePublisher(publisher);
    }

    @Test
    void testUpdatePublisher_InvalidId() throws Exception {
        Long publisherId = 1L;
        when(publisherService.findPublisher(publisherId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/publisher/update/{publisherId}", publisherId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(publisher)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Invalid ID!!, Please enter valid publisher ID"));

        verify(publisherService, never()).savePublisher(any(Publisher.class));
    }

    
    @Test
    void testDeletepublisher_Success() throws Exception {
      Long publisherId = 1L;
      doNothing().when(publisherService).delectPublisher(publisherId);

      mockMvc.perform(delete("/api/publisher/delete/{id}", publisherId)
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content().string("Publisher deleted Successfully"));
  }

  @Test
    void testDeletePublisher_NotFound() throws Exception {
      Long publisherId = 1L;
      doThrow(new PublisherNotFoundException("Publisher not found")).when(publisherService).delectPublisher(publisherId);

      mockMvc.perform(delete("/api/publisher/delete/{id}", publisherId)
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNotFound())
              .andExpect(content().string("Publisher not found"));
  }




}
