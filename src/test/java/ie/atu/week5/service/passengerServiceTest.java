package ie.atu.week5.service;

import ie.atu.week5.model.passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class passengerServiceTest {

    private passengerService service;

    @BeforeEach
    void setup() {
        service = new passengerService();
    }

    @Test
    void createThenFindById() {
        passenger p = passenger.builder()
                .passengerId("S1")
                .name("Steve")
                .email("Steve@atu.ie")
                .build();

        service.create(p);

        Optional<passenger> found = service.findById("S1");
        assertTrue(found.isPresent());
        assertEquals("Steve", found.get().getName());
    }

    @Test
    void duplicateIdThrows() {
        service.create(passenger.builder()
                .passengerId("B2")
                .name("Bob")
                .email("bob@atu.ie")
                .build());

        assertThrows(IllegalArgumentException.class, () ->
                service.create(passenger.builder()
                        .passengerId("B2")
                        .name("Bobby")
                        .email("bob@atu.ie")
                        .build())
        );
    }
}