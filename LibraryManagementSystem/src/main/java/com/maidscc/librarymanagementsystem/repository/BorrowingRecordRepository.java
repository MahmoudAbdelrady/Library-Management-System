package com.maidscc.librarymanagementsystem.repository;

import com.maidscc.librarymanagementsystem.model.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    @Query("SELECT br FROM BorrowingRecord br WHERE br.book.bookId = :bookId AND br.patron.patronId = :patronId")
    BorrowingRecord findBorrowingRecordByBookIdAndPatronId(@Param("bookId") UUID bookId, @Param("patronId") UUID patronId);
}
