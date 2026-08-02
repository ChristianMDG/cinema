package com.cinema.app.cinema.controller;

import com.cinema.app.cinema.entity.Reservation;
import com.cinema.app.cinema.security.AuthenticatedUser;
import com.cinema.app.cinema.service.ReservationService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

  @GetMapping
  @PreAuthorize("hasAnyRole('MANAGER','EMPLOYEE')") // 403 CLIENT, 200 MANAGER & EMPLOYEE
  public ResponseEntity<List<Reservation>> getAll() {
    return ResponseEntity.ok(reservationService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Reservation> getById(
      @PathVariable UUID id, @AuthenticationPrincipal AuthenticatedUser principal) {
    // MANAGER & EMPLOYEE : accès total
    if ("MANAGER".equals(principal.role()) || "EMPLOYEE".equals(principal.role())) {
      return ResponseEntity.ok(reservationService.findById(id));
    }
    // CLIENT : uniquement sa propre réservation, sinon 403
    if ("CLIENT".equals(principal.role())) {
      if (reservationService.isOwnedBy(id, principal.id())) {
        return ResponseEntity.ok(reservationService.findById(id));
      }
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @PostMapping
  public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
    return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.create(reservation));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('MANAGER','EMPLOYEE')") // 403 CLIENT, 200 EMPLOYEE & MANAGER
  public ResponseEntity<Reservation> update(
      @PathVariable UUID id, @RequestBody Reservation reservation) {
    return ResponseEntity.ok(reservationService.update(id, reservation));
  }
}
