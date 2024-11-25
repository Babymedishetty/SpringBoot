package com.OnlineLibrary.System.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException ex, WebRequest request){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(AuthorNotFoundException.class)
	public ResponseEntity<String> handleAuthorNotFoundException(AuthorNotFoundException ex, WebRequest request){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(PublisherNotFoundException.class)
	public ResponseEntity<String> handlePublisherNotFoundException(PublisherNotFoundException ex, WebRequest request){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
//	 @ExceptionHandler(EntityNotFoundException.class)
//	 @ResponseStatus(HttpStatus.NOT_FOUND)
//	 public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
//	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//	    }
	
	     
	    
	

}
