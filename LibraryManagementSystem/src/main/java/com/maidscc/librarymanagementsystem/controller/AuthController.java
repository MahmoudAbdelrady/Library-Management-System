package com.maidscc.librarymanagementsystem.controller;

import com.maidscc.librarymanagementsystem.dto.Patron.PatronLoginDTO;
import com.maidscc.librarymanagementsystem.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<Object> PatronLogin(@RequestBody @Valid PatronLoginDTO patronLoginDTO) throws Exception {
        return authService.patronLogin(patronLoginDTO);
    }
}
