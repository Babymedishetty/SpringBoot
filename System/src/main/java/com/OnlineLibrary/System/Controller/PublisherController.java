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
import com.OnlineLibrary.System.Exception.PublisherNotFoundException;
import com.OnlineLibrary.System.Service.PublisherService;

@RestController
@RequestMapping("/api/publisher")
public class PublisherController {
	
	private final PublisherService publisherService;
    

    @Autowired  
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
         
    }
	
	@PostMapping("/create")
	public ResponseEntity<String> createPublisher(@RequestBody Publisher publisher){
		try {
            if (publisher == null || publisher.getName() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Publisher cannot be null or empty");
            }
            publisherService.savePublisher(publisher);
            return ResponseEntity.status(HttpStatus.CREATED).body("Publisher created successfully");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
	}
	
	@GetMapping("/getAll")
    public ResponseEntity<List<Publisher>> getAllPublisher() {
        try {
            List<Publisher> publisher = publisherService.getAllPublisher();
            return new ResponseEntity<>(publisher, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<Publisher>getPublisherById(@PathVariable Long id) {
		Publisher publisher= publisherService.getPublisherById(id);
		if(publisher==null) {
			return ResponseEntity.notFound().build();
		}
		 return ResponseEntity.ok(publisher);
	}
	
	@PutMapping("/update/{publisherId}")
	public ResponseEntity<Object> updatePublisher(@RequestBody Publisher publisher, @PathVariable Long publisherId) {
		ResponseDto responseDto = new ResponseDto();
		Optional<Publisher> optional = publisherService.findPublisher(publisherId);
	    if (optional.isEmpty()) {
	        responseDto.setMessage("Invalid ID!!, Please enter valid publisher ID");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
	    }
	    Publisher publisherDB = optional.get();
	    publisherDB.setName(publisher.getName());
	    publisherDB.setContactNumber(publisher.getContactNumber());
	    publisherDB.setAddress(publisher.getAddress());
	    publisherService.savePublisher(publisherDB);
	    responseDto.setMessage("Publisher Details Updated");
	    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

	
	 @DeleteMapping("/delete/{id}")
	    public ResponseEntity<String> deletePublisher(@PathVariable Long id) {
	        try {
	            publisherService.delectPublisher(id);
	            return ResponseEntity.ok("Publisher deleted Successfully");
	        } catch (PublisherNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        }
	    }
	
	


}
