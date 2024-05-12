package com.maidscc.librarymanagementsystem.service;

import com.maidscc.librarymanagementsystem.dto.Patron.PatronDTO;
import com.maidscc.librarymanagementsystem.dto.Patron.PatronInfo;
import com.maidscc.librarymanagementsystem.dto.Patron.PatronRegisterDTO;
import com.maidscc.librarymanagementsystem.model.Patron;
import com.maidscc.librarymanagementsystem.repository.PatronRepository;
import com.maidscc.librarymanagementsystem.util.Response.ResponseMaker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatronService implements UserDetailsService {
    private final PatronRepository patronRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PatronService(PatronRepository patronRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.patronRepository = patronRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Cacheable(value = "patrons", key = "#root.methodName")
    public ResponseEntity<Object> getAllPatrons(Integer page) throws Exception {
        try {
            if (page == null) {
                page = 0;
            }
            PageRequest pageRequest = PageRequest.of(page, 10);
            List<Patron> patrons = patronRepository.findAll(pageRequest).getContent();
            List<PatronDTO> patronDTOS = patrons.stream().map(patron -> modelMapper.map(patron, PatronDTO.class)).toList();
            return ResponseEntity.ok(ResponseMaker.successRes("Patrons retrieved successfully", patronDTOS));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Cacheable(value = "patron", key = "#patronId")
    public ResponseEntity<Object> getPatronById(UUID patronId) throws Exception {
        try {
            Patron patron = patronRepository.findByPatronId(patronId);
            if (patron == null) {
                return ResponseEntity.ok(ResponseMaker.failRes("Patron not found"));
            }
            PatronDTO patronDTO = modelMapper.map(patron, PatronDTO.class);
            return ResponseEntity.ok(ResponseMaker.successRes("Patron retrieved successfully", patronDTO));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @CacheEvict(value = {"patrons", "patron"}, key = "#root.methodName", allEntries = true)
    public ResponseEntity<Object> registerPatron(PatronRegisterDTO patronRegisterDTO) throws Exception {
        try {
            Patron existingPatron = patronRepository.findByUsernameOrEmailOrPhoneNumber(patronRegisterDTO.getUsername(), patronRegisterDTO.getEmail(), patronRegisterDTO.getPhoneNumber());
            if (existingPatron != null) {
                if (existingPatron.getUsername().equals(patronRegisterDTO.getUsername())) {
                    return ResponseEntity.ok(ResponseMaker.failRes("Username already exists"));
                }
                if (existingPatron.getEmail().equals(patronRegisterDTO.getEmail())) {
                    return ResponseEntity.ok(ResponseMaker.failRes("Email already exists"));
                }
                return ResponseEntity.ok(ResponseMaker.failRes("Phone number already exists"));
            }
            patronRegisterDTO.setPassword(passwordEncoder.encode(patronRegisterDTO.getPassword()));
            Patron patron = modelMapper.map(patronRegisterDTO, Patron.class);
            patronRepository.save(patron);
            return ResponseEntity.ok(ResponseMaker.successRes("Patron Registered successfully", "No data"));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @CacheEvict(value = {"patrons", "patron"}, key = "#root.methodName", allEntries = true)
    public ResponseEntity<Object> updatePatron(UUID patronId, PatronDTO patronDTO) throws Exception {
        try {
            Patron patron = patronRepository.findByPatronId(patronId);
            if (patron == null) {
                return ResponseEntity.ok(ResponseMaker.failRes("Patron not found"));
            }
            PatronInfo patronInfo = (PatronInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!UUID.fromString(patronInfo.getPatronId()).equals(patronId)) {
                return ResponseEntity.badRequest().body(ResponseMaker.failRes("Unauthorized access"));
            }
            patron.setName(patronDTO.getName());
            Patron existingPatron = patronRepository.findByPhoneNumber(patronDTO.getPhoneNumber());
            if (existingPatron != null && !existingPatron.getPatronId().equals(patronId)) {
                return ResponseEntity.ok(ResponseMaker.failRes("Phone number already exists"));
            }
            patron.setPhoneNumber(patronDTO.getPhoneNumber());
            if (patronDTO.getAddress().isBlank()) {
                patronDTO.setAddress(null);
            }
            patron.setAddress(patronDTO.getAddress());
            patron.setDateOfBirth(patronDTO.getDateOfBirth());
            patron = patronRepository.save(patron);
            modelMapper.map(patron, patronDTO);
            return ResponseEntity.ok(ResponseMaker.successRes("Patron updated successfully", patronDTO));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @CacheEvict(value = {"patrons", "patron"}, key = "#root.methodName", allEntries = true)
    public ResponseEntity<Object> deletePatron(UUID patronId) throws Exception {
        try {
            Patron patron = patronRepository.findByPatronId(patronId);
            if (patron == null) {
                return ResponseEntity.ok(ResponseMaker.failRes("Patron not found"));
            }
            PatronInfo patronInfo = (PatronInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!UUID.fromString(patronInfo.getPatronId()).equals(patronId)) {
                return ResponseEntity.badRequest().body(ResponseMaker.failRes("Unauthorized access"));
            }
            patronRepository.delete(patron);
            return ResponseEntity.ok(ResponseMaker.successRes("Patron deleted successfully", "No data"));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Patron patron = patronRepository.findByUsernameOrEmail(username, username);
        if (patron == null) {
            throw new UsernameNotFoundException("Patron not found");
        }
        return patron;
    }
}
