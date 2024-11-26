package com.OnlineLibrary.System.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.OnlineLibrary.System.Entity.Author;
import com.OnlineLibrary.System.Exception.AuthorNotFoundException;
import com.OnlineLibrary.System.Repository.AuthorRepository;

@Service
public class AuthorService {
	
	private final AuthorRepository authorRepository;
	   
    @Autowired  
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

	public void saveAuthor(Author author) {
		authorRepository.save(author);
		 }

	 public List<Author> getAllAuthors() {
	        List<Author> authors = authorRepository.findAll();
	        if (authors.isEmpty()) {
	            throw new AuthorNotFoundException("author not found");
	        }
	        return authors;
	    }

	public Author getAuthorById(Long id) {
		return  authorRepository.findById(id).orElseThrow(()-> new
				AuthorNotFoundException("Author not found"));
		
	}

	public void delectAuthor(Long id) {
		 authorRepository.deleteById(id);
		
	}

	public Optional<Author> findAuthor(Long authorId) {
		 return authorRepository.findById(authorId);
		 
	}
	
	
 
	

 
}
