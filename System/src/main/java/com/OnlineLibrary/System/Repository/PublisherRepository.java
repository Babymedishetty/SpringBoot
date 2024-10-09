package com.OnlineLibrary.System.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.OnlineLibrary.System.Entity.Publisher;
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

}
