package com.maidscc.librarymanagementsystem.repository;

import com.maidscc.librarymanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByBookId(UUID bookId);
    Book findByTitleIgnoreCaseOrIsbn(String title, String isbn);
}
