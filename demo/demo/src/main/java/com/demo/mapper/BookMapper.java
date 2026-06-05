package com.demo.mapper;

import com.demo.dto.BookResDto;
import com.demo.model.Book;

public class BookMapper {

    public static BookResDto entityToDto(Book book) {
        return new BookResDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getAuthor().getEmail()
        );
    }
}
