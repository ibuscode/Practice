package com.demo.controller;

import com.demo.dto.BookResDto;
import com.demo.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class BookController {

    private final BookService bookService;
    public List<BookResDto> getBookByAuthor(@Valid
                                            @RequestParam(defaultValue = "0")int page,
                                            @RequestParam(defaultValue = "10")int size,
                                            Principal principal) {
        String username = principal.getName();
        return bookService.getBookByAuthor(username);

    }
}
