package com.bus.validation;

import com.bus.model.Bus;
import com.bus.model.Driver;

/**
 * Validates bus-related rules B1 to B5.
 */
public class BusValidator {

    public static boolean isValidBusID(String busID) {
        return busID != null
                && busID.length() == 8
                && busID.matches("\\d{8}");
    }

    public static boolean canUpdateCapacity(Bus existingBus, int newCapacity) {
        if (existingBus == null || newCapacity <= 0) {
            return false;
        }

        return newCapacity <= existingBus.getCapacity();
    }

    public static boolean canDriverOperateBus(Driver driver, Bus bus) {
        if (driver == null || bus == null) {
            return false;
        }

        int driverAge = DriverValidator.calculateAge(driver.getBirthdate());

        if (driverAge > 50 && bus.getCapacity() >= 50) {
            return false;
        }

        if (bus.getFuelType().equalsIgnoreCase("Electricity")
                && driver.getExperienceYears() < 5) {
            return false;
        }

        if ((bus.getFuelType().equalsIgnoreCase("Electricity")
                || bus.getFuelType().equalsIgnoreCase("Hybrid"))
                && !(driver.getLicenseType().equalsIgnoreCase("Heavy")
                || driver.getLicenseType().equalsIgnoreCase("PublicTransport"))) {
            return false;
        }

        return true;
    }

    public static boolean isValidBus(Bus bus) {
        if (bus == null) {
            return false;
        }

        return isValidBusID(bus.getBusID())
                && bus.getCapacity() > 0
                && bus.getFuelLevel() >= 0
                && bus.getFuelLevel() <= 100
                && bus.getFuelType() != null
                && !bus.getFuelType().isBlank();
    }
}