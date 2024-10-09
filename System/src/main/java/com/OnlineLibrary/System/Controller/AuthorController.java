package com.OnlineLibrary.System.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OnlineLibrary.System.Entity.Author;
import com.OnlineLibrary.System.Service.AuthorService;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
	@Autowired
	private AuthorService authorService;
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
	
	
	@DeleteMapping("/delete/{id}")
	public String deleteAuthor(@PathVariable Long id) {
		authorService.delectAuthor(id);
		return "Book deleted Successfully";
	}

}
