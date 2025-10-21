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
// Homework Tests
    @Test
    void updatePassenger() {
        // Make a passenger first
        passenger p = passenger.builder()
                .passengerId("U1")
                .name("Old Name")
                .email("old@atu.ie")
                .build();
        service.create(p);

        // New info to update
        passenger updated = passenger.builder()
                .passengerId("U1")
                .name("New Name")
                .email("new@atu.ie")
                .build();

        // Update should work
        Optional<passenger> result = service.update("U1", updated);
        assertTrue(result.isPresent());
        assertEquals("New Name", result.get().getName());
        assertEquals("new@atu.ie", result.get().getEmail());

        // Updating something that doesn’t exist should return empty
        assertTrue(service.update("missing", updated).isEmpty());
    }

    @Test
    void deletePassenger() {
        // Add one to delete
        passenger p = passenger.builder()
                .passengerId("D1")
                .name("Delete Me")
                .email("delete@atu.ie")
                .build();
        service.create(p);

        // First delete should work
        assertTrue(service.delete("D1"));

        // Deleting again should fail
        assertFalse(service.delete("D1"));

        // Check it’s really gone
        assertTrue(service.findById("D1").isEmpty());
    }

}