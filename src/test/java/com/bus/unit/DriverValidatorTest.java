package com.bus.unit;

import com.bus.model.Driver;
import com.bus.validation.DriverValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DriverValidatorTest {

    // D1 Driver ID Rules

    @Test
    void validDriverIDShouldReturnTrue() {
        assertTrue(DriverValidator.isValidDriverID("56@@abCDXY"));
    }

    @Test
    void driverIDWithInvalidLengthShouldReturnFalse() {
        assertFalse(DriverValidator.isValidDriverID("56@@abCDX"));
    }

    @Test
    void driverIDWithInvalidStartingDigitsShouldReturnFalse() {
        assertFalse(DriverValidator.isValidDriverID("11@@abCDXY"));
    }

    // D2 Address Format

    @Test
    void validAddressShouldReturnTrue() {
        assertTrue(DriverValidator.isValidAddress("12|King Street|Melbourne|VIC|Australia"));
    }

    @Test
    void addressWithoutPipesShouldReturnFalse() {
        assertFalse(DriverValidator.isValidAddress("12 King Street Melbourne VIC Australia"));
    }

    @Test
    void addressWithMissingPartShouldReturnFalse() {
        assertFalse(DriverValidator.isValidAddress("12|King Street|Melbourne|VIC"));
    }

    // D3 Birthdate Format

    @Test
    void validBirthdateShouldReturnTrue() {
        assertTrue(DriverValidator.isValidBirthdate("15-08-1995"));
    }

    @Test
    void birthdateWithWrongFormatShouldReturnFalse() {
        assertFalse(DriverValidator.isValidBirthdate("1995-08-15"));
    }

    @Test
    void impossibleBirthdateShouldReturnFalse() {
        assertFalse(DriverValidator.isValidBirthdate("31-02-1995"));
    }

    // D4 License Update Restriction

    @Test
    void driverWithMoreThanTenYearsCannotChangeLicense() {
        Driver existing = new Driver("56@@abCDXY", "John Smith", 12,
                "Heavy", "12|King Street|Melbourne|VIC|Australia", "15-08-1985");

        assertFalse(DriverValidator.canUpdateLicense(existing, "PublicTransport"));
    }

    @Test
    void driverWithMoreThanTenYearsCanKeepSameLicense() {
        Driver existing = new Driver("56@@abCDXY", "John Smith", 12,
                "Heavy", "12|King Street|Melbourne|VIC|Australia", "15-08-1985");

        assertTrue(DriverValidator.canUpdateLicense(existing, "Heavy"));
    }

    @Test
    void driverWithTenYearsOrLessCanChangeLicense() {
        Driver existing = new Driver("56@@abCDXY", "John Smith", 10,
                "Medium", "12|King Street|Melbourne|VIC|Australia", "15-08-1985");

        assertTrue(DriverValidator.canUpdateLicense(existing, "Heavy"));
    }

    // D5 Immutable Fields

    @Test
    void unchangedDriverIDAndNameShouldReturnTrue() {
        Driver existing = new Driver("56@@abCDXY", "John Smith", 5,
                "Heavy", "12|King Street|Melbourne|VIC|Australia", "15-08-1995");

        Driver updated = new Driver("56@@abCDXY", "John Smith", 6,
                "Heavy", "99|Queen Street|Melbourne|VIC|Australia", "15-08-1995");

        assertTrue(DriverValidator.areImmutableFieldsUnchanged(existing, updated));
    }

    @Test
    void changedDriverIDShouldReturnFalse() {
        Driver existing = new Driver("56@@abCDXY", "John Smith", 5,
                "Heavy", "12|King Street|Melbourne|VIC|Australia", "15-08-1995");

        Driver updated = new Driver("57@@abCDXY", "John Smith", 6,
                "Heavy", "99|Queen Street|Melbourne|VIC|Australia", "15-08-1995");

        assertFalse(DriverValidator.areImmutableFieldsUnchanged(existing, updated));
    }

    @Test
    void changedDriverNameShouldReturnFalse() {
        Driver existing = new Driver("56@@abCDXY", "John Smith", 5,
                "Heavy", "12|King Street|Melbourne|VIC|Australia", "15-08-1995");

        Driver updated = new Driver("56@@abCDXY", "Jack Smith", 6,
                "Heavy", "99|Queen Street|Melbourne|VIC|Australia", "15-08-1995");

        assertFalse(DriverValidator.areImmutableFieldsUnchanged(existing, updated));
    }
}