package com.OnlineLibrary.System.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OnlineLibrary.System.Entity.Author;
import com.OnlineLibrary.System.Repository.AuthorRepository;

@Service
public class AuthorService {
	@Autowired
	private AuthorRepository authorRepository;

	public void saveAuthor(Author author) {
		authorRepository.save(author);
		 }

	public List<Author> getAllAuthor() {
		 
		return authorRepository.findAll();
	}

	public Author getAuthorById(Long id) {
		 
		return authorRepository.findById(id).orElse(null);
	}

	public void delectAuthor(Long id) {
		 authorRepository.deleteById(id);
		
	}

	public Optional<Author> findAuthor(Long authorId) {
		 Optional<Author>author=authorRepository.findById(authorId);
		return author;
	}
	
	//hlo 

}
