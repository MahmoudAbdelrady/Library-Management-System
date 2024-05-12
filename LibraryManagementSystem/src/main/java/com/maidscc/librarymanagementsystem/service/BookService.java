package com.maidscc.librarymanagementsystem.service;

import com.maidscc.librarymanagementsystem.dto.BookDTO;
import com.maidscc.librarymanagementsystem.model.Book;
import com.maidscc.librarymanagementsystem.repository.BookRepository;
import com.maidscc.librarymanagementsystem.util.Response.ResponseMaker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Cacheable(value = "books", key = "#root.methodName")
    public ResponseEntity<Object> getAllBooks(Integer page) throws Exception {
        try {
            if (page == null) {
                page = 0;
            }
            PageRequest pageRequest = PageRequest.of(page, 10);
            List<Book> books = bookRepository.findAll(pageRequest).getContent();
            List<BookDTO> bookDTOS = books.stream().map(book -> modelMapper.map(book, BookDTO.class)).toList();
            return ResponseEntity.ok(ResponseMaker.successRes("Books retrieved successfully", bookDTOS));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Cacheable(value = "book", key = "#bookId")
    public ResponseEntity<Object> getBookById(UUID bookId) throws Exception {
        try {
            Book book = bookRepository.findByBookId(bookId);
            if (book == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMaker.failRes("Book not found"));
            }
            BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
            return ResponseEntity.ok(ResponseMaker.successRes("Book retrieved successfully", bookDTO));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @CacheEvict(value = {"books", "book"}, key = "#root.methodName", allEntries = true)
    public ResponseEntity<Object> createBook(BookDTO bookDTO) throws Exception {
        try {
            Book existingBook = bookRepository.findByTitleIgnoreCaseOrIsbn(bookDTO.getTitle(), bookDTO.getIsbn());
            if (existingBook != null) {
                if (existingBook.getTitle().equalsIgnoreCase(bookDTO.getTitle())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseMaker.failRes("Book already exists"));
                }
                return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseMaker.failRes("ISBN already assigned to another book"));
            }
            Book book = modelMapper.map(bookDTO, Book.class);
            book = bookRepository.save(book);
            modelMapper.map(book, bookDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMaker.successRes("Book created successfully", bookDTO));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @CacheEvict(value = {"books", "book"}, key = "#root.methodName", allEntries = true)
    public ResponseEntity<Object> updateBook(UUID bookId, BookDTO bookDTO) throws Exception {
        try {
            Book book = bookRepository.findByBookId(bookId);
            if (book == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMaker.failRes("Book not found"));
            }
            Book existingBook = bookRepository.findByTitleIgnoreCaseOrIsbn(bookDTO.getTitle(), bookDTO.getIsbn());
            if (existingBook != null && !existingBook.getBookId().equals(bookId)) {
                if (existingBook.getTitle().equalsIgnoreCase(bookDTO.getTitle())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseMaker.failRes("Book already exists"));
                }
                return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseMaker.failRes("ISBN already assigned to another book"));
            }
            bookDTO.setBookId(null);
            bookDTO.setAddedAt(null);
            modelMapper.map(bookDTO, book);
            book = bookRepository.save(book);
            modelMapper.map(book, bookDTO);
            return ResponseEntity.ok(ResponseMaker.successRes("Book updated successfully", bookDTO));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @CacheEvict(value = {"books", "book"}, key = "#root.methodName", allEntries = true)
    public ResponseEntity<Object> deleteBook(UUID bookId) throws Exception {
        try {
            Book book = bookRepository.findByBookId(bookId);
            if (book == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMaker.failRes("Book not found"));
            }
            bookRepository.delete(book);
            return ResponseEntity.ok(ResponseMaker.successRes("Book deleted successfully", "No data"));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
