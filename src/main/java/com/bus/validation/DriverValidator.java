package com.bus.validation;

import com.bus.model.Driver;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * Validates driver-related rules D1 to D5.
 */
public class DriverValidator {

    public static boolean isValidDriverID(String driverID) {
        if (driverID == null || driverID.length() != 10) {
            return false;
        }

        if (!Character.isDigit(driverID.charAt(0)) || !Character.isDigit(driverID.charAt(1))) {
            return false;
        }

        int firstDigit = Character.getNumericValue(driverID.charAt(0));
        int secondDigit = Character.getNumericValue(driverID.charAt(1));

        if (firstDigit < 2 || firstDigit > 9 || secondDigit < 2 || secondDigit > 9) {
            return false;
        }

        int specialCount = 0;

        for (int i = 2; i <= 7; i++) {
            char character = driverID.charAt(i);

            if (!Character.isLetterOrDigit(character)) {
                specialCount++;
            }
        }

        if (specialCount < 2) {
            return false;
        }

        return Character.isUpperCase(driverID.charAt(8))
                && Character.isUpperCase(driverID.charAt(9));
    }

    public static boolean isValidAddress(String address) {
        if (address == null) {
            return false;
        }

        String[] parts = address.split("\\|");

        return parts.length == 5
                && !parts[0].isBlank()
                && !parts[1].isBlank()
                && !parts[2].isBlank()
                && !parts[3].isBlank()
                && !parts[4].isBlank();
    }

    public static boolean isValidBirthdate(String birthdate) {
        if (birthdate == null) {
            return false;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("dd-MM-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

            LocalDate.parse(birthdate, formatter);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public static int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        LocalDate dateOfBirth = LocalDate.parse(birthdate, formatter);

        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public static boolean canUpdateLicense(Driver existingDriver, String newLicenseType) {
        if (existingDriver == null || newLicenseType == null) {
            return false;
        }

        if (existingDriver.getExperienceYears() > 10
                && !existingDriver.getLicenseType().equals(newLicenseType)) {
            return false;
        }

        return true;
    }

    public static boolean areImmutableFieldsUnchanged(Driver existingDriver, Driver updatedDriver) {
        if (existingDriver == null || updatedDriver == null) {
            return false;
        }

        return existingDriver.getDriverID().equals(updatedDriver.getDriverID())
                && existingDriver.getName().equals(updatedDriver.getName());
    }

    public static boolean isValidDriver(Driver driver) {
        if (driver == null) {
            return false;
        }

        return isValidDriverID(driver.getDriverID())
                && isValidAddress(driver.getAddress())
                && isValidBirthdate(driver.getBirthdate());
    }
}