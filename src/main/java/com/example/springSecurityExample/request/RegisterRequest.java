package com.example.springSecurityExample.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterRequest (
        @NotBlank(message = "il nickname non può essere una stringa vuota, ma deve contenere dei caratteri")
        @Size(min = 5, max = 30, message = "il nickname deve avere una lunghezza compresa tra 5 e 30 caratteri")
        String nickname,
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        String email,
        String password
) { }
