package com.webservice.sample.service;

import com.webservice.sample.dto.BookRequest;
import com.webservice.sample.dto.BookResponse;
import com.webservice.sample.entity.Book;
import com.webservice.sample.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public BookResponse create(BookRequest request) {
        Book saved = repository.save(new Book(request)); // INSERT
        return BookResponse.from(saved);
    }

    public List<BookResponse> findAll() {
        return repository.findAll().stream().map(BookResponse::from).toList();
    }

    public BookResponse findById(Long id) {
        return BookResponse.from(findEntity(id));
    }

    public BookResponse update(Long id, BookRequest request) {
        Book book = findEntity(id);
        book.update(request);
        // Week 09: explicitly save (Week 10 changes this to Dirty Checking).
        return BookResponse.from(repository.save(book));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found: " + id);
        }
        repository.deleteById(id);
    }

    private Book findEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Book not found: " + id));
    }
}
