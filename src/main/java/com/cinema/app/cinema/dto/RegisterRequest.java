package com.cinema.app.cinema.dto;

import com.cinema.app.cinema.entity.UserRole;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record RegisterRequest(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotNull @Past LocalDate birthdate,
    @NotBlank @Email String email,
    @NotBlank @Size(min = 8, message = "Password must be at least 8 characters") String password,
    String phone,
    @NotNull UserRole role) {}
