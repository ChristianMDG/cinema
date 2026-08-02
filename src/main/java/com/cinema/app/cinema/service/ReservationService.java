package com.cinema.app.cinema.service;

import com.cinema.app.cinema.entity.Reservation;
import com.cinema.app.cinema.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation findById(UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found: " + id));
    }

    public Reservation create(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation update(UUID id, Reservation updated) {
        Reservation existing = findById(id);
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setSeats(updated.getSeats());
        return reservationRepository.save(existing);
    }

    public boolean isOwnedBy(UUID reservationId, UUID userId) {
        Reservation reservation = findById(reservationId);
        return reservation.getUser() != null && reservation.getUser().getId().equals(userId);
    }
}