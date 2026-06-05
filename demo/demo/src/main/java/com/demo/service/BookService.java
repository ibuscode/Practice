package com.demo.service;

import com.demo.dto.BookResDto;
import com.demo.mapper.BookMapper;
import com.demo.model.Author;
import com.demo.model.Book;
import com.demo.repository.AuthorRepository;
import com.demo.repository.BookRepository;
import com.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

    public List<BookResDto> getBookByAuthor(String username) {

        Author author = authorRepository.findByUsername(username);

        List<Book> books = bookRepository.findByAuthorId(author.getId());

        return books.stream()
                .map(BookMapper::entityToDto)
                .toList();
    }
}
