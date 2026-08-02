package com.cinema.app.cinema.controller;

import com.cinema.app.cinema.dto.AuthResponse;
import com.cinema.app.cinema.dto.LoginRequest;
import com.cinema.app.cinema.dto.RegisterRequest;
import com.cinema.app.cinema.entity.User;
import com.cinema.app.cinema.security.JwtService;
import com.cinema.app.cinema.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;
  private final JwtService jwtService;

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
    User user =
        User.builder()
            .firstName(request.firstName())
            .lastName(request.lastName())
            .birthdate(request.birthdate())
            .email(request.email())
            .password(request.password())
            .phone(request.phone())
            .role(request.role())
            .build();

    User saved = userService.register(user);
    String token =
        jwtService.generateToken(saved.getId(), saved.getEmail(), saved.getRole().name());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new AuthResponse(token, saved.getId(), saved.getEmail(), saved.getRole().name()));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
    User user = userService.authenticate(request.email(), request.password());
    String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole().name());

    return ResponseEntity.ok(
        new AuthResponse(token, user.getId(), user.getEmail(), user.getRole().name()));
  }
}
