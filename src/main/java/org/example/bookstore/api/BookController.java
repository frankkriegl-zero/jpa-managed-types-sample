package org.example.bookstore.api;

import external.model.Book;
import org.example.bookstore.repository.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) Integer year) {
        if (year != null) {
            return bookRepository.findByPublicationYear(year);
        } else {
            return bookRepository.findAll();
        }
    }
}