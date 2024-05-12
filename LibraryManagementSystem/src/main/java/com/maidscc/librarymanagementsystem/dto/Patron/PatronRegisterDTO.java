package com.maidscc.librarymanagementsystem.dto.Patron;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatronRegisterDTO {
    @NotNull(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z]{3,}$", message = "Name should contain only letters and be at least 3 characters long")
    private String name;

    @NotNull(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z0-9_]{4,}$", message = "Username should contain only letters, underscore, numbers and be at least 4 characters long")
    private String username;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password is required")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
            message = "Password should contain at least one uppercase letter, one lowercase letter, one number, one special character and be at least 8 characters long")
    private String password;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^(?:\\+?20|0)(?:10|11|12|15)\\d{8}$", message = "Please enter a valid egyptian number")
    private String phoneNumber;
}
