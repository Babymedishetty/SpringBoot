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

@Entity
@Data
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
	public Long getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public Publisher(Long publisherId, String name, String address, String contactNumber, List<Book> books) {
		super();
		this.publisherId = publisherId;
		this.name = name;
		this.address = address;
		this.contactNumber = contactNumber;
		this.books = books;
	}
	@Override
	public String toString() {
		return "Publisher [publisherId=" + publisherId + ", name=" + name + ", address=" + address + ", contactNumber="
				+ contactNumber + ", books=" + books + "]";
	}
	public Publisher() {
		
	}
	
	

}
