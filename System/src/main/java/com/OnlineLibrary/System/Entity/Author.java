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
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
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
	
	
	
	
	
	
	
	
	
	
	 public Author(String firstName, String lastName) {
	        this.firstName = firstName;
	        this.lastName = lastName;
	    }

}
