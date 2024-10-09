package com.OnlineLibrary.System.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OnlineLibrary.System.Entity.Publisher;
import com.OnlineLibrary.System.Repository.PublisherRepository;

@Service
public class PublisherService {
	@Autowired
	private PublisherRepository publisherRepository;
	

	public void savePublisher(Publisher publisher) {
		publisherRepository.save(publisher);
		 }

	public List<Publisher> getAllPublisher() { 
		return publisherRepository.findAll();
	}


	public Publisher getPublisherrById(Long id) {
		 return publisherRepository.findById(id).orElse(null);
	}


	public void delectPublisher(Long id) {
		publisherRepository.deleteById(id);
		 }

}
