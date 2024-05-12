package com.maidscc.librarymanagementsystem.service;

import com.maidscc.librarymanagementsystem.dto.Patron.PatronInfo;
import com.maidscc.librarymanagementsystem.model.Book;
import com.maidscc.librarymanagementsystem.model.BorrowingRecord;
import com.maidscc.librarymanagementsystem.model.Patron;
import com.maidscc.librarymanagementsystem.repository.BookRepository;
import com.maidscc.librarymanagementsystem.repository.BorrowingRecordRepository;
import com.maidscc.librarymanagementsystem.repository.PatronRepository;
import com.maidscc.librarymanagementsystem.util.Response.ResponseMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class BorrowingRecordService {
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;

    @Autowired
    public BorrowingRecordService(BorrowingRecordRepository borrowingRecordRepository, BookRepository bookRepository,
                                  PatronRepository patronRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }

    public ResponseEntity<Object> borrowBook(UUID bookId, UUID patronId) throws Exception {
        try {
            Book book = bookRepository.findByBookId(bookId);
            if (book == null) {
                return ResponseEntity.badRequest().body(ResponseMaker.failRes("Book not found"));
            }
            Patron patron = patronRepository.findByPatronId(patronId);
            if (patron == null) {
                return ResponseEntity.badRequest().body(ResponseMaker.failRes("Patron not found"));
            }
            PatronInfo patronInfo = (PatronInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!UUID.fromString(patronInfo.getPatronId()).equals(patronId)) {
                return ResponseEntity.badRequest().body(ResponseMaker.failRes("Unauthorized access"));
            }
            BorrowingRecord existingRecord = borrowingRecordRepository.findBorrowingRecordByBookIdAndPatronId(bookId, patronId);
            if (existingRecord != null) {
                if (existingRecord.getReturnDate() == null) {
                    return ResponseEntity.badRequest().body(ResponseMaker.failRes("Book already borrowed by this patron"));
                }
                existingRecord.setBorrowingDate(LocalDate.now());
                existingRecord.setReturnDate(null);
                borrowingRecordRepository.save(existingRecord);
                return ResponseEntity.ok(ResponseMaker.successRes("Book borrowed successfully", "No data"));
            }
            BorrowingRecord record = new BorrowingRecord();
            record.setBorrowingDate(LocalDate.now());
            record.setBook(book);
            record.setPatron(patron);
            borrowingRecordRepository.save(record);
            return ResponseEntity.ok(ResponseMaker.successRes("Book borrowed successfully", "No data"));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public ResponseEntity<Object> returnBook(UUID bookId, UUID patronId) throws Exception {
        try {
            Book book = bookRepository.findByBookId(bookId);
            if (book == null) {
                return ResponseEntity.badRequest().body(ResponseMaker.failRes("Book not found"));
            }
            Patron patron = patronRepository.findByPatronId(patronId);
            if (patron == null) {
                return ResponseEntity.badRequest().body(ResponseMaker.failRes("Patron not found"));
            }
            PatronInfo patronInfo = (PatronInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!UUID.fromString(patronInfo.getPatronId()).equals(patronId)) {
                return ResponseEntity.badRequest().body(ResponseMaker.failRes("Unauthorized access"));
            }
            BorrowingRecord record = borrowingRecordRepository.findBorrowingRecordByBookIdAndPatronId(bookId, patronId);
            if (record == null) {
                return ResponseEntity.badRequest().body(ResponseMaker.failRes("No borrowing record found"));
            }
            if (record.getReturnDate() != null) {
                return ResponseEntity.badRequest().body(ResponseMaker.failRes("Book already returned"));
            }
            record.setReturnDate(LocalDate.now());
            borrowingRecordRepository.save(record);
            return ResponseEntity.ok(ResponseMaker.successRes("Book returned successfully", "No data"));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
