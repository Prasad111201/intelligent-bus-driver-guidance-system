package com.bus.integration;

import com.bus.model.Bus;
import com.bus.repository.BusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class BusRepositoryIntegrationTest {

    private BusRepository repository;

    @BeforeEach
    void setup() {
        File file = new File("src/main/resources/buses.json");

        if (file.exists()) {
            file.delete();
        }

        repository = new BusRepository("src/main/resources/buses.json");
    }

    @Test
    void addBusShouldIncreaseCount() {
        Bus bus = new Bus("12345678", 45, 90.0, "Diesel");

        assertTrue(repository.addBus(bus));
        assertEquals(1, repository.countBuses());
    }

    @Test
    void retrieveBusShouldReturnStoredBus() {
        Bus bus = new Bus("12345678", 45, 90.0, "Diesel");

        repository.addBus(bus);

        Bus retrieved = repository.retrieveBus("12345678");

        assertNotNull(retrieved);
        assertEquals(45, retrieved.getCapacity());
    }

    @Test
    void duplicateBusShouldNotBeAdded() {
        Bus bus = new Bus("12345678", 45, 90.0, "Diesel");

        repository.addBus(bus);

        assertFalse(repository.addBus(bus));
    }

    @Test
    void updateBusShouldSucceed() {
        Bus bus = new Bus("12345678", 45, 90.0, "Diesel");

        repository.addBus(bus);

        Bus updated = new Bus("12345678", 40, 80.0, "Hybrid");

        assertTrue(repository.updateBus(updated));
    }
}