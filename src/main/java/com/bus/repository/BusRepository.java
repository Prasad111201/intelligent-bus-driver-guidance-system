package com.bus.repository;

import com.bus.model.Bus;
import com.bus.validation.BusValidator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for storing, retrieving, updating and counting buses using JSON files.
 */
public class BusRepository {

    private final File file;
    private final ObjectMapper mapper = new ObjectMapper();

    public BusRepository(String filePath) {
        this.file = new File(filePath);
    }

    private List<Bus> readBuses() {
        try {
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }

            return mapper.readValue(file, new TypeReference<List<Bus>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void writeBuses(List<Bus> buses) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, buses);
        } catch (Exception e) {
            throw new RuntimeException("Unable to write buses to file.");
        }
    }

    public boolean addBus(Bus bus) {
        List<Bus> buses = readBuses();

        if (!BusValidator.isValidBus(bus)) {
            return false;
        }

        if (retrieveBus(bus.getBusID()) != null) {
            return false;
        }

        buses.add(bus);
        writeBuses(buses);
        return true;
    }

    public Bus retrieveBus(String busID) {
        List<Bus> buses = readBuses();

        for (Bus bus : buses) {
            if (bus.getBusID().equals(busID)) {
                return bus;
            }
        }

        return null;
    }

    public boolean updateBus(Bus updatedBus) {
        List<Bus> buses = readBuses();

        for (int i = 0; i < buses.size(); i++) {
            Bus existingBus = buses.get(i);

            if (existingBus.getBusID().equals(updatedBus.getBusID())) {

                if (!BusValidator.canUpdateCapacity(existingBus, updatedBus.getCapacity())) {
                    return false;
                }

                if (!BusValidator.isValidBus(updatedBus)) {
                    return false;
                }

                buses.set(i, updatedBus);
                writeBuses(buses);
                return true;
            }
        }

        return false;
    }

    public int countBuses() {
        return readBuses().size();
    }
}