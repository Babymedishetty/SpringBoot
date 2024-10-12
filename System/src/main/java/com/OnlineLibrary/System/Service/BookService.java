package com.OnlineLibrary.System.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OnlineLibrary.System.Entity.Book;
import com.OnlineLibrary.System.Repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;

	public void saveBook(Book book) {
		bookRepository.save(book);
		 
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public Book getBookById(Long id) {
		return bookRepository.findById(id).orElse(null);
	}
	
	public List<Book> getBooksByAuthor(String firstName) {
		 
		return bookRepository.findByAuthorFirstName(firstName);
	}
	
	 
	public void delectBook(Long id) {
		 bookRepository.deleteById(id);
		
	}
	
	public List<Book>searchBooks(String title,String author,String publisher){
		List<Book>allBooks=bookRepository.findAll();
		return allBooks.stream()
				.filter(book->(title==null || book.getTitle().equalsIgnoreCase(title)))
				.filter(book->(author==null || (book.getAuthor()!=null && book.getAuthor().getFirstName().equalsIgnoreCase(author))))
				.filter(book->(publisher==null || (book.getPublisher()!=null && book.getPublisher().getName().equalsIgnoreCase(publisher))))
				.collect(Collectors.toList());
		
	}

	public List<Book> sortBooksByTitle() {
		 
		return bookRepository.findAll().stream()
				.sorted((book1,book2)->book1.getTitle().compareToIgnoreCase(book2.getTitle()))
				.collect(Collectors.toList());
	}

	public Map<String, Long> getBookCountByAuthor() {
		 List<Book>allBooks=bookRepository.findAll();
		return allBooks.stream()
				.filter(book->book.getAuthor()!=null)
				.collect(Collectors.groupingBy(book->book.getAuthor().getFirstName()+" "+ book.getAuthor().getLastName(),
						Collectors.counting()));
	}

	public Optional<Book> findBook(Long bookId) {
		Optional<Book> book= bookRepository.findById(bookId);
		return book;
	}
	
	

	

	

	

}
