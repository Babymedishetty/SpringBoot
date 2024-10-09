package com.OnlineLibrary.System.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.OnlineLibrary.System.Entity.Author;
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
