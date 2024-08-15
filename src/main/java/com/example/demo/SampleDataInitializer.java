package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
@Log4j2
class SampleDataInitializer {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final DatabaseClient databaseClient;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {

        Flux<Reservation> reservations = reservationService
                .saveAll("Madhura", "josh", "Olga", "Marcin", "Ria", "Stéphane", "Violetta", "Dr. Syer");

        this.reservationRepository
                .deleteAll()
                .thenMany(reservations)
                .thenMany(this.reservationRepository.findAll())
                .subscribe(log::info);
    }
}