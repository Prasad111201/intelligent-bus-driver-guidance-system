package com.bus.integration;

import com.bus.model.Driver;
import com.bus.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class DriverRepositoryIntegrationTest {

    private DriverRepository repository;

    @BeforeEach
    void setup() {
        File file = new File("src/main/resources/drivers.json");

        if (file.exists()) {
            file.delete();
        }

        repository = new DriverRepository("src/main/resources/drivers.json");
    }

    @Test
    void addDriverShouldIncreaseCount() {
        Driver driver = new Driver(
                "56@@abCDXY",
                "John Smith",
                5,
                "Heavy",
                "12|King Street|Melbourne|VIC|Australia",
                "15-08-1995"
        );

        assertTrue(repository.addDriver(driver));
        assertEquals(1, repository.countDrivers());
    }

    @Test
    void retrieveDriverShouldReturnStoredDriver() {
        Driver driver = new Driver(
                "56@@abCDXY",
                "John Smith",
                5,
                "Heavy",
                "12|King Street|Melbourne|VIC|Australia",
                "15-08-1995"
        );

        repository.addDriver(driver);

        Driver retrieved = repository.retrieveDriver("56@@abCDXY");

        assertNotNull(retrieved);
        assertEquals("John Smith", retrieved.getName());
    }

    @Test
    void duplicateDriverShouldNotBeAdded() {
        Driver driver = new Driver(
                "56@@abCDXY",
                "John Smith",
                5,
                "Heavy",
                "12|King Street|Melbourne|VIC|Australia",
                "15-08-1995"
        );

        repository.addDriver(driver);

        assertFalse(repository.addDriver(driver));
    }

    @Test
    void updateDriverShouldSucceed() {
        Driver driver = new Driver(
                "56@@abCDXY",
                "John Smith",
                5,
                "Heavy",
                "12|King Street|Melbourne|VIC|Australia",
                "15-08-1995"
        );

        repository.addDriver(driver);

        Driver updated = new Driver(
                "56@@abCDXY",
                "John Smith",
                6,
                "Heavy",
                "99|Queen Street|Melbourne|VIC|Australia",
                "15-08-1995"
        );

        assertTrue(repository.updateDriver(updated));
    }
}