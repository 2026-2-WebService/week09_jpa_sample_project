package com.webservice.sample.repository;

import com.webservice.sample.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Week 09: All basic CRUD methods are inherited from JpaRepository.
}
