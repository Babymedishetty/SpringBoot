package com.OnlineLibrary.System.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OnlineLibrary.System.Entity.Book;
import com.OnlineLibrary.System.Service.BookService;

@RestController
@RequestMapping("/api/book")
public class BookController {
	
	@Autowired
	private BookService bookService;
	@PostMapping("/insert")
	public ResponseEntity<Book> insertBook(@RequestBody Book book){
		bookService.saveBook(book);
		return ResponseEntity.ok(book);
		
	}
	@GetMapping("/getAllBooks")
	public List<Book> getAllBooks(){
		return bookService.getAllBooks();
	}
	
	@GetMapping("/{id}")
	public Book getBookById(@PathVariable Long id) {
		return bookService.getBookById(id);
	}
	
	@GetMapping("/Author")
	public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam String firstName){
		List<Book> books=bookService.getBooksByAuthor(firstName);
		if(books.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(books);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteBook(@PathVariable Long id) {
		bookService.delectBook(id);
		return "Book deleted Successfully";
	}
	@GetMapping("/search")
	public ResponseEntity<List<Book>> searchBooks(
			@RequestParam(required=false)String title,
			@RequestParam(required=false)String author,
			@RequestParam(required=false)String publisher){
		List<Book>books=bookService.searchBooks(title,author,publisher);
		if(books.isEmpty()) {
			return ResponseEntity.noContent().build();
			
		}else {
			return ResponseEntity.ok(books);
		}
		
	}
	@GetMapping("/sort")
	public ResponseEntity<List<Book>> sortBooksByTitle(){
		List<Book>sortedBooks=bookService.sortBooksByTitle();
		if(sortedBooks.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		else {
			return ResponseEntity.ok(sortedBooks);
		}
	}
	@GetMapping("/report")
	public ResponseEntity<Map<String,Long>>getBooksCountByAuthor(){
		Map<String,Long>report=bookService.getBookCountByAuthor();
		if(report.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		else {
			return ResponseEntity.ok(report);
		}
		
	}

}
