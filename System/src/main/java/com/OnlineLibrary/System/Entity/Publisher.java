package com.OnlineLibrary.System.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
public class Publisher {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long publisherId;
	
	private String name;
	
	private String address;
	
	private String contactNumber;
	
	@JsonIgnore
	@OneToMany(mappedBy="publisher",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Book>books;
	
	
	
	
	
	
	
	
	  public Publisher(String name) {
	        this.name = name;
	    }

}
