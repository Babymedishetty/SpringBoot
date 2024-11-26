package com.OnlineLibrary.System.Controller;

import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OnlineLibrary.System.Dto.ResponseDto;
import com.OnlineLibrary.System.Entity.Book;
import com.OnlineLibrary.System.Exception.BookNotFoundException;

import com.OnlineLibrary.System.Repository.AuthorRepository;
import com.OnlineLibrary.System.Repository.PublisherRepository;
import com.OnlineLibrary.System.Service.BookService;

@RestController
@RequestMapping("/api/book")
public class BookController {
	
	 private final BookService bookService;
	    private final AuthorRepository authorRepository;
	    private final PublisherRepository publisherRepository;

	    @Autowired  
	    public BookController(BookService bookService, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
	        this.bookService = bookService;
	        this.authorRepository = authorRepository;
	        this.publisherRepository = publisherRepository;
	    }
	 
	@PostMapping("/insert")
	    public ResponseEntity<String> insertBook(@RequestBody Book book) {
		 
		    if (book.getAuthor() == null) {
		        return ResponseEntity.badRequest().body("Author information is missing.");
		    }
		 
	        if (!authorRepository.existsById(book.getAuthor().getAuthorId())) {
	            return new ResponseEntity<>("Author with ID " + book.getAuthor().getAuthorId() + " not present in the system. Please enter a valid ID.", HttpStatus.NOT_FOUND);
	        }

	        if (!publisherRepository.existsById(book.getPublisher().getPublisherId())) {
	            return new ResponseEntity<>("Publisher with ID " + book.getPublisher().getPublisherId() + " not present in the system. Please enter a valid ID.", HttpStatus.NOT_FOUND);
	        }

	        
	            bookService.saveBook(book);
	            return ResponseEntity.ok("Book created successfully.");
	         
	    }
	 
	@GetMapping("/getAllBooks")
	public List<Book> getAllBooks(){
		return bookService.getAllBooks();
	}
	
	 
	 @GetMapping("/{id}")
     public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }
	
	@PutMapping("/update/{bookId}")
    public ResponseEntity updateBook(@RequestBody Book book, @PathVariable Long bookId){
		ResponseDto responseDto = new ResponseDto();
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
	    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
	        try {
	            bookService.delectBook(id);
	            return ResponseEntity.ok("Book deleted Successfully");
	        } catch (BookNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        }
	    }
	
	@GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String publisher) {

        List<Book> books = bookService.searchBooks(title, author, publisher);

        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(books, HttpStatus.OK);
    }
	@GetMapping("/sort")
    public ResponseEntity<List<Book>> sortBooksByTitle() {
        List<Book> sortedBooks = bookService.sortBooksByTitle();
        if (sortedBooks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sortedBooks, HttpStatus.OK);
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
