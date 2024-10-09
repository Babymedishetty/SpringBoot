package com.OnlineLibrary.System.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Book {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long bookId;
	
	private String title;
	@ManyToOne
	@JoinColumn(name="author_id", nullable=false)
	private Author author;
	@ManyToOne
	@JoinColumn(name="publisher_id", nullable=false)
	private Publisher publisher;
	
	private double price;
	
	private int pageCount;
	
	private String language;
	
	private double rating;
	
	private String genre;

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Book(Long bookId, String title, Author author, Publisher publisher, double price, int pageCount,
			String language, double rating, String genre) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.pageCount = pageCount;
		this.language = language;
		this.rating = rating;
		this.genre = genre;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", title=" + title + ", author=" + author + ", publisher=" + publisher
				+ ", price=" + price + ", pageCount=" + pageCount + ", language=" + language + ", rating=" + rating
				+ ", genre=" + genre + "]";
	}

	public Book() {
		 
	}
	
	



}
