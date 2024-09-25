package com.likelion.lionlib.controller;

import com.likelion.lionlib.dto.request.ReservationRequest;
import com.likelion.lionlib.dto.response.ReservationResponse;
import com.likelion.lionlib.dto.response.ReserveCountResponse;
import com.likelion.lionlib.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
        log.info("Creating new reservation: {}", request);
        ReservationResponse newReservation = reservationService.addReserve(request);
        return ResponseEntity.ok(newReservation);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable("id") Long id) {
        log.info("Fetching reservation with ID: {}", id);
        ReservationResponse reservation = reservationService.getReserve(id);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable("id") Long id) {
        log.info("Canceling reservation with ID: {}", id);
        reservationService.deleteReserve(id);
        return ResponseEntity.ok("Reservation canceled successfully.");
    }

    @GetMapping("/members/{memberId}/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservationsByMember(@PathVariable("memberId") Long memberId) {
        log.info("Fetching reservations for member ID: {}", memberId);
        List<ReservationResponse> reservations = reservationService.getMemberReserve(memberId);

        if (reservations.isEmpty()) {
            log.warn("No reservations found for member ID: {}", memberId);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/books/{bookId}/reservations")
    public ResponseEntity<ReserveCountResponse> getReservationCountForBook(@PathVariable("bookId") Long bookId) {
        log.info("Fetching reservation count for book ID: {}", bookId);
        ReserveCountResponse count = reservationService.countReserve(bookId);
        return ResponseEntity.ok(count);
    }
}
