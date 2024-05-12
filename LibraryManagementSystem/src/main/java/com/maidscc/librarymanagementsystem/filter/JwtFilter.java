package com.maidscc.librarymanagementsystem.filter;

import com.maidscc.librarymanagementsystem.dto.Patron.PatronInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Value("${maids.secretKey}")
    private String tokenSecretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtTokenHeader != null && jwtTokenHeader.startsWith("Bearer ")) {
            try {
                jwtTokenHeader = jwtTokenHeader.substring(7);
                SecretKey secretKey = Keys.hmacShaKeyFor(tokenSecretKey.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwtTokenHeader).getPayload();
                String patronId = claims.get("id", String.class);
                String username = claims.get("username", String.class);
                PatronInfo patronInfo = new PatronInfo(patronId, username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(patronInfo, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException | IllegalArgumentException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/login") || (request.getMethod().equals("POST") && request.getServletPath().equals("/api/patrons"));
    }
}
