package com.cinema.app.cinema.repository;

import com.cinema.app.cinema.entity.Reservation;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
  List<Reservation> findByUserId(UUID userId);
}
