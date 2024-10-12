package com.OnlineLibrary.System.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OnlineLibrary.System.Dto.ResponseDto;
import com.OnlineLibrary.System.Entity.Publisher;
import com.OnlineLibrary.System.Service.PublisherService;

@RestController
@RequestMapping("/api/publisher")
public class PublisherController {
	@Autowired
	private PublisherService publisherService;
	@Autowired
	private ResponseDto responseDto;
	@PostMapping("/create")
	public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher){
		publisherService.savePublisher(publisher);
		return ResponseEntity.ok(publisher);
	}
	
	@GetMapping("/getAll")
	public List<Publisher> getAllPublisher(){
		return publisherService.getAllPublisher();
	}
	
	@GetMapping("/{id}")
	public Publisher getPublisherById(@PathVariable Long id) {
		return publisherService.getPublisherrById(id);
	}
	
	@PutMapping("/update/{publisherId}")
	public ResponseEntity updatePublisher(@RequestBody Publisher publisher, @PathVariable Long publisherId) {
		Optional<Publisher>optional=publisherService.findPublisher(publisherId);
		if(optional.isEmpty()) {
			responseDto.setMessage("Invalid ID!!, Please enter valid publisher ID");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
		}
		Publisher publisherDB=optional.get();
		publisherDB.setName(publisher.getName());
		publisherDB.setContactNumber(publisher.getContactNumber());
		publisherDB.setAddress(publisher.getAddress());
		publisherService.savePublisher(publisherDB);
		responseDto.setMessage("Publisher Details Updated");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
		
		
	}
	
	@DeleteMapping("/delete/{id}")
	public String deletePublisher(@PathVariable Long id) {
		publisherService.delectPublisher(id);
		return "Book deleted Successfully";
	}
	
	


}
