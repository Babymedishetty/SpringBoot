package com.OnlineLibrary.System.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.OnlineLibrary.System.Entity.Author;
import com.OnlineLibrary.System.Exception.AuthorNotFoundException;
import com.OnlineLibrary.System.Exception.BookNotFoundException;
import com.OnlineLibrary.System.Service.AuthorService;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
	
	 
	 private final AuthorService authorService;

	    @Autowired
	    public AuthorController(AuthorService authorService) {
	        this.authorService = authorService;
	    }
	    
	    @PostMapping("/create")
	    public ResponseEntity<String> createAuthor(@RequestBody Author author) {
	        try {
	            if (author == null || author.getFirstName() == null || author.getLastName() == null) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Author cannot be null or empty");
	            }
	            authorService.saveAuthor(author);
	            return ResponseEntity.status(HttpStatus.CREATED).body("Author created successfully");
	        } catch (Exception ex) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
	        }
	    }
	
 
	    
	    @GetMapping("/getAll")
	    public ResponseEntity<List<Author>> getAllAuthor() {
	        try {
	            List<Author> authors = authorService.getAllAuthors();
	            return new ResponseEntity<>(authors, HttpStatus.OK);
	        } catch (RuntimeException e) {
	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	
	@GetMapping("/{id}")
	public ResponseEntity<Author>getAuthorById(@PathVariable Long id) {
		Author author= authorService.getAuthorById(id);
		if(author==null) {
			return ResponseEntity.notFound().build();
		}
		 return ResponseEntity.ok(author);
	}
	
	@PutMapping("/update/{authorId}")
	public ResponseEntity<Object> updateAuthor(@RequestBody Author author, @PathVariable Long authorId) {
		ResponseDto responseDto = new ResponseDto();
		Optional<Author>optional=authorService.findAuthor(authorId);
		if(optional.isEmpty()) {
			responseDto.setMessage("Invalid ID!!, Please enter valid author ID");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
			}
		Author authorDB=optional.get();
		authorDB.setFirstName(author.getFirstName());
		authorDB.setLastName(author.getLastName());
		authorDB.setNationality(author.getNationality());
		authorService.saveAuthor(authorDB);
		responseDto.setMessage("Author Details Updated");
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}
	
	
	@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        try {
            authorService.delectAuthor(id);
            return ResponseEntity.ok("author deleted Successfully");
        } catch (AuthorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
