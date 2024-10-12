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
import com.OnlineLibrary.System.Entity.Author;
import com.OnlineLibrary.System.Service.AuthorService;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
	@Autowired
	private AuthorService authorService;
	@Autowired
	private ResponseDto responseDto;
	@PostMapping("/create")
	public ResponseEntity<Author> CeateAuthor(@RequestBody Author author){
		authorService.saveAuthor(author);
		return ResponseEntity.ok(author);
		
	}
	
	@GetMapping("/getAll")
	public List<Author> getAllAuthor(){
		return authorService.getAllAuthor();
	}
	
	@GetMapping("/{id}")
	public Author getAuthorById(@PathVariable Long id) {
		return authorService.getAuthorById(id);
	}
	
	@PutMapping("/update/{authorId}")
	public ResponseEntity updateAuthor(@RequestBody Author author, @PathVariable Long authorId) {
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
		responseDto.setMessage("Author Details Updated ");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public String deleteAuthor(@PathVariable Long id) {
		authorService.delectAuthor(id);
		return "Author deleted Successfully";
	}
	

}
