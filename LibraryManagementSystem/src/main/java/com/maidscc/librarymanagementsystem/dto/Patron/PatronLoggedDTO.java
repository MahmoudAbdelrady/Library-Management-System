package com.maidscc.librarymanagementsystem.dto.Patron;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PatronLoggedDTO {
    private String username;
    private String email;
    private String token;
}
