package com.OnlineLibrary.System.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
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
	
	
	
	
	
	
	
	
	
	
	
	
	public Book(String title, Author author, Publisher publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

}
