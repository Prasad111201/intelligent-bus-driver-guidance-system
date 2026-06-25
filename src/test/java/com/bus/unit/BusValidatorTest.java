package com.bus.unit;

import com.bus.model.Bus;
import com.bus.model.Driver;
import com.bus.validation.BusValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BusValidatorTest {

    // B1 Bus ID Rules

    @Test
    void validBusIDShouldReturnTrue() {
        assertTrue(BusValidator.isValidBusID("12345678"));
    }

    @Test
    void busIDWithLettersShouldReturnFalse() {
        assertFalse(BusValidator.isValidBusID("1234ABCD"));
    }

    @Test
    void busIDWithInvalidLengthShouldReturnFalse() {
        assertFalse(BusValidator.isValidBusID("1234567"));
    }

    // B2 Capacity Update Restriction

    @Test
    void decreasedCapacityShouldReturnTrue() {
        Bus existing = new Bus("12345678", 60, 80.0, "Diesel");
        assertTrue(BusValidator.canUpdateCapacity(existing, 50));
    }

    @Test
    void sameCapacityShouldReturnTrue() {
        Bus existing = new Bus("12345678", 60, 80.0, "Diesel");
        assertTrue(BusValidator.canUpdateCapacity(existing, 60));
    }

    @Test
    void increasedCapacityShouldReturnFalse() {
        Bus existing = new Bus("12345678", 60, 80.0, "Diesel");
        assertFalse(BusValidator.canUpdateCapacity(existing, 70));
    }

    // B3 Driver Age Restriction

    @Test
    void driverOlderThan50CannotDriveLargeBus() {
        Driver driver = new Driver("56@@abCDXY", "John Smith", 20,
                "Heavy", "12|King Street|Melbourne|VIC|Australia", "15-08-1970");

        Bus bus = new Bus("12345678", 50, 80.0, "Diesel");

        assertFalse(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    void driverOlderThan50CanDriveSmallBus() {
        Driver driver = new Driver("56@@abCDXY", "John Smith", 20,
                "Heavy", "12|King Street|Melbourne|VIC|Australia", "15-08-1970");

        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    void driverAge50CanDriveLargeBus() {
        Driver driver = new Driver("56@@abCDXY", "John Smith", 20,
                "Heavy", "12|King Street|Melbourne|VIC|Australia", "15-08-1976");

        Bus bus = new Bus("12345678", 50, 80.0, "Diesel");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }

    // B4 Electric Bus Restriction

    @Test
    void driverWithLessThanFiveYearsCannotDriveElectricBus() {
        Driver driver = new Driver("56@@abCDXY", "John Smith", 4,
                "Heavy", "12|King Street|Melbourne|VIC|Australia", "15-08-1990");

        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");

        assertFalse(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    void driverWithFiveYearsCanDriveElectricBus() {
        Driver driver = new Driver("56@@abCDXY", "John Smith", 5,
                "Heavy", "12|King Street|Melbourne|VIC|Australia", "15-08-1990");

        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    void experiencedDriverCanDriveElectricBus() {
        Driver driver = new Driver("56@@abCDXY", "John Smith", 8,
                "PublicTransport", "12|King Street|Melbourne|VIC|Australia", "15-08-1990");

        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }

    // B5 Driver Licence Restriction

    @Test
    void lightLicenceCannotDriveHybridBus() {
        Driver driver = new Driver("56@@abCDXY", "John Smith", 8,
                "Light", "12|King Street|Melbourne|VIC|Australia", "15-08-1990");

        Bus bus = new Bus("12345678", 40, 80.0, "Hybrid");

        assertFalse(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    void heavyLicenceCanDriveHybridBus() {
        Driver driver = new Driver("56@@abCDXY", "John Smith", 8,
                "Heavy", "12|King Street|Melbourne|VIC|Australia", "15-08-1990");

        Bus bus = new Bus("12345678", 40, 80.0, "Hybrid");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    void publicTransportLicenceCanDriveElectricBus() {
        Driver driver = new Driver("56@@abCDXY", "John Smith", 8,
                "PublicTransport", "12|King Street|Melbourne|VIC|Australia", "15-08-1990");

        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }
}