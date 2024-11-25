package com.OnlineLibrary.System.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OnlineLibrary.System.Entity.Author;
import com.OnlineLibrary.System.Entity.Publisher;
import com.OnlineLibrary.System.Exception.PublisherNotFoundException;
import com.OnlineLibrary.System.Repository.PublisherRepository;

@Service
public class PublisherService {
	private final PublisherRepository publisherRepository; 

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) { 
        this.publisherRepository = publisherRepository; 
    } 
	

	public void savePublisher(Publisher publisher) {
		publisherRepository.save(publisher);
		 }

	public List<Publisher> getAllPublisher() { 
		return publisherRepository.findAll();
	}


	public Publisher getPublisherById(Long id) {
		 return publisherRepository.findById(id).orElseThrow(()-> new PublisherNotFoundException("Publisher not found"));
	}


	public void delectPublisher(Long id) {
		publisherRepository.deleteById(id);
		 }

	 
	public Optional<Publisher> findPublisher(Long publisherId) {
		  return publisherRepository.findById(publisherId);
		 
	}

}
