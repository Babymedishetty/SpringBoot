package com.OnlineLibrary.System.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.OnlineLibrary.System.Entity.Author;
import com.OnlineLibrary.System.Entity.Book;
@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

	@Query("SELECT b FROM Book b WHERE b.author.firstName=:firstName")
	List<Book> findByAuthorFirstName(String firstName);

	boolean existsByTitleAndAuthor(String title, Author author);

	 
	 

}
