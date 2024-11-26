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

import com.OnlineLibrary.System.Entity.Publisher;
import com.OnlineLibrary.System.Exception.PublisherNotFoundException;
import com.OnlineLibrary.System.Repository.PublisherRepository;
import com.OnlineLibrary.System.Service.PublisherService;

class PublisherServiceTest {

 @Mock
 private PublisherRepository publisherRepository;

 @InjectMocks
 private PublisherService publisherService;

 @BeforeEach
 void setUp() {
     MockitoAnnotations.openMocks(this);
 }

 @Test
 void testSavePublisher() {
     Publisher publisher = new Publisher();
     publisherService.savePublisher(publisher);
     verify(publisherRepository, times(1)).save(publisher);
 }

 @Test
 void testGetAllPublisher() {
     List<Publisher> publishers = Arrays.asList(new Publisher(), new Publisher());
     when(publisherRepository.findAll()).thenReturn(publishers);

     List<Publisher> result = publisherService.getAllPublisher();
     assertEquals(2, result.size());
 }

 @Test
 void testGetPublisherById() {
     Publisher publisher = new Publisher();
     when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));

     Publisher result = publisherService.getPublisherById(1L);
     assertNotNull(result);
 }

 @Test
 void PublisherNotFoundException() {
     when(publisherRepository.findById(1L)).thenReturn(Optional.empty());

     assertThrows(PublisherNotFoundException.class, () -> {
         publisherService.getPublisherById(1L);
     });
 }

 @Test
 void testDeletePublisher() {
     publisherService.delectPublisher(1L);
     verify(publisherRepository, times(1)).deleteById(1L);
 }

 @Test
 void testFindPublisher() {
     Publisher publisher = new Publisher();
     when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));

     Optional<Publisher> result = publisherService.findPublisher(1L);
     assertTrue(result.isPresent());
 }
}

