# Intelligent Bus Driver Guidance System

## Student Information

**Name:** Prasad Kamalekar

**Course:** Software Engineering Fundamentals

---

## Project Overview

The Intelligent Bus Driver Guidance System is a Java-based application developed using Maven. The system demonstrates object-oriented software engineering principles by managing bus and driver information, validating business rules, storing data in JSON files, and verifying functionality through automated testing.

---

## Features

- Driver registration and management
- Bus registration and management
- Driver validation
- Bus validation
- JSON-based persistent storage
- Repository pattern implementation
- Unit testing using JUnit 5
- Integration testing
- Continuous Integration using GitHub Actions

---

## Technologies Used

- Java 25
- Maven
- JUnit 5
- Jackson Databind
- Git
- GitHub
- GitHub Actions

---

## Project Structure

```
src
├── main
│   ├── java
│   │   └── com.bus
│   │       ├── model
│   │       ├── repository
│   │       └── validation
│   └── resources
│       ├── buses.json
│       └── drivers.json
└── test
    └── java
        └── com.bus
            ├── unit
            └── integration
```

---

## Validation Rules

### Driver

- Driver ID format validation
- Address validation
- Birthdate validation
- Immutable Driver ID and Name
- Licence update restrictions

### Bus

- Bus ID validation
- Capacity update restriction
- Driver age restriction
- Electric bus experience restriction
- Hybrid bus licence restriction

---

## Testing

The project includes automated testing using JUnit 5.

- 30 Unit Tests
- 8 Integration Tests

Total Tests: **38**

All tests execute successfully using Maven.

```
mvn clean test
```

---

## Continuous Integration

GitHub Actions automatically builds the project and executes all tests whenever code is pushed to the **main** branch.

---

## Repository

GitHub Repository:

https://github.com/Prasad111201/intelligent-bus-driver-guidance-system