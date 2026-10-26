package com.webservice.sample.entity;

import com.webservice.sample.dto.BookRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false, length = 50)
    private String category;

    protected Book() { } // JPA uses the no-args constructor.

    public Book(BookRequest request) {
        this.title = request.title();
        this.author = request.author();
        this.price = request.price();
        this.category = request.category();
    }

    public void update(BookRequest request) {
        this.title = request.title();
        this.author = request.author();
        this.price = request.price();
        this.category = request.category();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public Integer getPrice() { return price; }
    public String getCategory() { return category; }
}
