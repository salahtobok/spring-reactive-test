package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;

@Service
@Transactional
@RequiredArgsConstructor
class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TransactionalOperator transactionalOperator;

    public Flux<Reservation> saveAll(String... names) {
        Flux<Reservation> reservations = Flux
                .fromArray(names)
                .map(name -> new Reservation(null, name))
                .flatMap(this.reservationRepository::save)
                .doOnNext(this::assertValid)
                .onErrorContinue((e, r) -> {
                    // Log the error and continue processing other reservations
                    System.err.println("Error processing reservation: " + e.getMessage());
                });
        return reservations;
    }

    private void assertValid(Reservation r) {
        Assert.isTrue(r.getName() != null && r.getName().length() > 0
                && Character.isUpperCase(r.getName().charAt(0)), "the name must start with a capital letter");
    }
}
