package com.maidscc.librarymanagementsystem.controller;

import com.maidscc.librarymanagementsystem.dto.Patron.PatronDTO;
import com.maidscc.librarymanagementsystem.dto.Patron.PatronRegisterDTO;
import com.maidscc.librarymanagementsystem.service.PatronService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {
    private final PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public ResponseEntity<Object> GetAllPatrons(@RequestParam("p") @Nullable Integer page) throws Exception {
        return patronService.getAllPatrons(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> GetPatronById(@PathVariable UUID id) throws Exception {
        return patronService.getPatronById(id);
    }

    @PostMapping
    public ResponseEntity<Object> CreatePatron(@RequestBody @Valid PatronRegisterDTO patronRegisterDTO) throws Exception {
        return patronService.registerPatron(patronRegisterDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> UpdatePatron(@PathVariable UUID id, @RequestBody @Valid PatronDTO patronDTO) throws Exception {
        return patronService.updatePatron(id, patronDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> DeletePatron(@PathVariable UUID id) throws Exception {
        return patronService.deletePatron(id);
    }
}
