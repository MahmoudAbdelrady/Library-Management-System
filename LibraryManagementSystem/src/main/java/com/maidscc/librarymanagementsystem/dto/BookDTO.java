package com.maidscc.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private UUID bookId;

    @NotBlank(message = "Book title is required")
    private String title;

    @NotBlank(message = "Book author is required")
    private String author;

    @NotNull(message = "Book publication year is required")
    private Integer publicationYear;

    @NotNull(message = "Book ISBN is required")
    @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$", message = "Please enter a valid ISBN")
    private String isbn;

    @NotNull(message = "Book price is required")
    private BigDecimal price;

    private Date addedAt;

    public BookDTO(String title, String author, Integer publicationYear, String isbn, BigDecimal price) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.price = price;
    }
}
