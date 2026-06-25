package com.bus.repository;

import com.bus.model.Driver;
import com.bus.validation.DriverValidator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for storing, retrieving, updating and counting drivers using JSON files.
 */
public class DriverRepository {

    private final File file;
    private final ObjectMapper mapper = new ObjectMapper();

    public DriverRepository(String filePath) {
        this.file = new File(filePath);
    }

    private List<Driver> readDrivers() {
        try {
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }

            return mapper.readValue(file, new TypeReference<List<Driver>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void writeDrivers(List<Driver> drivers) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, drivers);
        } catch (Exception e) {
            throw new RuntimeException("Unable to write drivers to file.");
        }
    }

    public boolean addDriver(Driver driver) {
        List<Driver> drivers = readDrivers();

        if (!DriverValidator.isValidDriver(driver)) {
            return false;
        }

        if (retrieveDriver(driver.getDriverID()) != null) {
            return false;
        }

        drivers.add(driver);
        writeDrivers(drivers);
        return true;
    }

    public Driver retrieveDriver(String driverID) {
        List<Driver> drivers = readDrivers();

        for (Driver driver : drivers) {
            if (driver.getDriverID().equals(driverID)) {
                return driver;
            }
        }

        return null;
    }

    public boolean updateDriver(Driver updatedDriver) {
        List<Driver> drivers = readDrivers();

        for (int i = 0; i < drivers.size(); i++) {
            Driver existingDriver = drivers.get(i);

            if (existingDriver.getDriverID().equals(updatedDriver.getDriverID())) {

                if (!DriverValidator.areImmutableFieldsUnchanged(existingDriver, updatedDriver)) {
                    return false;
                }

                if (!DriverValidator.canUpdateLicense(existingDriver, updatedDriver.getLicenseType())) {
                    return false;
                }

                if (!DriverValidator.isValidDriver(updatedDriver)) {
                    return false;
                }

                drivers.set(i, updatedDriver);
                writeDrivers(drivers);
                return true;
            }
        }

        return false;
    }

    public int countDrivers() {
        return readDrivers().size();
    }
}