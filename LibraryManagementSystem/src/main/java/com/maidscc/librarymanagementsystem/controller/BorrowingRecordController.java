package com.maidscc.librarymanagementsystem.controller;

import com.maidscc.librarymanagementsystem.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class BorrowingRecordController {
    private final BorrowingRecordService borrowingRecordService;

    @Autowired
    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping("/api/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<Object> BorrowBook(@PathVariable UUID bookId, @PathVariable UUID patronId) throws Exception {
        return borrowingRecordService.borrowBook(bookId, patronId);
    }

    @PutMapping("/api/return/{bookId}/patron/{patronId}")
    public ResponseEntity<Object> ReturnBook(@PathVariable UUID bookId, @PathVariable UUID patronId) throws Exception {
        return borrowingRecordService.returnBook(bookId, patronId);
    }
}
