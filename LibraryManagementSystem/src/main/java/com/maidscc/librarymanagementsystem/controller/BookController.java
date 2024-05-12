package com.maidscc.librarymanagementsystem.controller;

import com.maidscc.librarymanagementsystem.dto.BookDTO;
import com.maidscc.librarymanagementsystem.service.BookService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Object> GetAllBooks(@RequestParam("p") @Nullable Integer page) throws Exception {
        return bookService.getAllBooks(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> GetBookById(@PathVariable UUID id) throws Exception {
        return bookService.getBookById(id);
    }

    @PostMapping
    public ResponseEntity<Object> CreateBook(@RequestBody @Valid BookDTO bookDTO) throws Exception {
        return bookService.createBook(bookDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> UpdateBook(@PathVariable UUID id, @RequestBody @Valid BookDTO bookDTO) throws Exception {
        return bookService.updateBook(id, bookDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> DeleteBook(@PathVariable UUID id) throws Exception {
        return bookService.deleteBook(id);
    }
}
