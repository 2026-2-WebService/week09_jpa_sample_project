package com.webservice.sample.dto;

import com.webservice.sample.entity.Book;

public record BookResponse(
        Long id, String title, String author, Integer price, String category
) {
    public static BookResponse from(Book book) {
        return new BookResponse(book.getId(), book.getTitle(), book.getAuthor(),
                book.getPrice(), book.getCategory());
    }
}
