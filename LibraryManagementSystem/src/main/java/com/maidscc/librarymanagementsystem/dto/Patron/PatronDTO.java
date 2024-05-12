package com.maidscc.librarymanagementsystem.dto.Patron;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatronDTO {
    private UUID patronId;

    @NotNull(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z]{3,}$", message = "Name should contain only letters and be at least 3 characters long")
    private String name;

    private String username;

    private String email;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^(?:\\+?20|0)(?:10|11|12|15)\\d{8}$", message = "Please enter a valid egyptian number")
    private String phoneNumber;

    @Pattern(regexp = "^[a-zA-Z0-9,\\s]*$", message = "Address should contain only letters, numbers, and commas")
    private String address;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date should be in the format yyyy-mm-dd")
    private String dateOfBirth;
}
