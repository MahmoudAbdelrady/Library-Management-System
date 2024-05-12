package com.maidscc.librarymanagementsystem.service;

import com.maidscc.librarymanagementsystem.dto.Patron.PatronLoggedDTO;
import com.maidscc.librarymanagementsystem.dto.Patron.PatronLoginDTO;
import com.maidscc.librarymanagementsystem.model.Patron;
import com.maidscc.librarymanagementsystem.util.Response.ResponseMaker;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthService {
    private final AuthenticationManager authManager;
    @Value("${maids.secretKey}")
    private String secretKey;

    @Autowired
    public AuthService(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    private String generateJWT(UUID patronId, String username) {
        LocalDateTime expiryDateTime = LocalDateTime.now().plusMonths(1);
        Date expiryDate = Date.from(expiryDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject("Token")
                .issuer("Maidscc")
                .claim("id", patronId)
                .claim("username", username)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .issuedAt(new Date())
                .expiration(expiryDate)
                .compact();
    }

    public ResponseEntity<Object> patronLogin(PatronLoginDTO patronLoginDTO) throws Exception {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(patronLoginDTO.getUsernameOrEmail(), patronLoginDTO.getPassword());
            Authentication authentication = authManager.authenticate(authToken);
            Patron patron = (Patron) authentication.getPrincipal();
            PatronLoggedDTO patronLoggedDTO = new PatronLoggedDTO();
            patronLoggedDTO.setUsername(patron.getUsername());
            patronLoggedDTO.setEmail(patron.getEmail());
            patronLoggedDTO.setToken(generateJWT(patron.getPatronId(), patron.getUsername()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok(ResponseMaker.successRes("Logged in successfully", patronLoggedDTO));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseMaker.failRes("Invalid credentials"));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
