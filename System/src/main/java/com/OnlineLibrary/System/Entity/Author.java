package com.OnlineLibrary.System.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Author {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long authorId;
	
	private String firstName;
	
	private String lastName;
	
	private String nationality;
	@JsonIgnore
	@OneToMany(mappedBy="author", cascade=jakarta.persistence.CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Book> books;

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public Author(Long authorId, String firstName, String lastName, String nationality, List<Book> books) {
		super();
		this.authorId = authorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.nationality = nationality;
		this.books = books;
	}

	@Override
	public String toString() {
		return "Author [authorId=" + authorId + ", firstName=" + firstName + ", lastName=" + lastName + ", nationality="
				+ nationality + ", books=" + books + "]";
	}
	
	public Author() {
		
	}
	
	

}
