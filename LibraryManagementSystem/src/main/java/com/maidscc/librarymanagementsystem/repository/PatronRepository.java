package com.maidscc.librarymanagementsystem.repository;

import com.maidscc.librarymanagementsystem.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatronRepository extends JpaRepository<Patron, Long> {
    Patron findByPatronId(UUID patronId);
    Patron findByUsernameOrEmail(String username, String email);
    Patron findByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);
    Patron findByPhoneNumber(String phoneNumber);
}
