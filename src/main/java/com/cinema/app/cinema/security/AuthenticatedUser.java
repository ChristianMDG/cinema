package com.cinema.app.cinema.security;

import java.util.UUID;

public record AuthenticatedUser(UUID id, String email, String role) {}
