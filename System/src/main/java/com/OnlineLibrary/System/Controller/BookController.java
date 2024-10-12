package com.OnlineLibrary.System.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OnlineLibrary.System.Dto.ResponseDto;
import com.OnlineLibrary.System.Entity.Book;
import com.OnlineLibrary.System.Service.BookService;

@RestController
@RequestMapping("/api/book")
public class BookController {
	
	@Autowired
	private BookService bookService;
	@Autowired
	private ResponseDto responseDto;
	
	
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
	
	@PutMapping("/update/{bookId}")
    public ResponseEntity updateBook(@RequestBody Book book, @PathVariable Long bookId){
        Optional<Book> optional= bookService.findBook(bookId);
        if (!optional.isPresent()){
            responseDto.setMessage("Invalid ID!!, Please enter valid ID");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
        Book bookDB =optional.get();
        bookDB.setTitle(book.getTitle());
        bookDB.setAuthor(book.getAuthor());
        bookDB.setGenre(book.getGenre());
        bookDB.setLanguage(book.getLanguage());
        bookDB.setPageCount(book.getPageCount());
        bookDB.setPrice(book.getPrice());
        bookDB.setPublisher(book.getPublisher());
        bookDB.setRating(book.getRating());
        bookService.saveBook(bookDB);
        responseDto.setMessage("Book Details Updated");
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
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
