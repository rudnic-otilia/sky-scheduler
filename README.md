# Air Traffic Control System

## Overview

This project is an **Air Traffic Control System** designed to manage aircraft takeoffs and landings at an airport. It provides functionalities for handling airplanes, assigning them to runways, managing runway priorities, and executing maneuvers. The system efficiently processes commands from input files, ensuring smooth operation through **collections, generics, exception handling, and OOP principles**.

## Features

- **Airplane Management**: Handles airplane creation, status updates, and scheduling.
- **Runway Allocation**: Dynamically assigns airplanes to runways while enforcing constraints.
- **Priority Queues for Runways**: Ensures optimal scheduling of takeoffs and landings.
- **Exception Handling**: Detects and reports incorrect runway assignments or unavailable runways.
- **Command Processing**: Parses and executes commands from structured input files.

## Implemented Classes

### **Airplane**
- Represents an aircraft with attributes such as model, ID, departure, destination, desired takeoff/landing time, actual takeoff/landing time, and flight status.

### **AirplaneManager**
- Manages airplane allocation to runways and stores airplanes efficiently using a **Map**, ensuring quick access by ID.

### **Runway**
- Generic class that accepts only airplane objects and maintains a **priority queue** for takeoffs and landings based on predefined rules.

### **RunwayManager**
- Oversees runway operations, including adding runways and granting maneuver permissions.
- Uses a **Map** for fast lookup of runways by ID.
- Implements logic for handling **permission_for_maneuver** commands.

### **NarrowBodyAirplane & WideBodyAirplane**
- Specialized subclasses of `Airplane` that differentiate aircraft types.

### **CommandManager**
- Parses commands from input files and directs execution to appropriate classes.
- Handles exceptions such as `UnavailableRunwayException` and `IncorrectRunwayException`.

### **PrintInfo**
- Manages **flight_info** and **runway_info** commands.
- Stores data in **Maps** for efficient retrieval and reporting.

### **Main**
- Reads input commands and directs them to `CommandManager`.
- Handles errors and writes outputs to corresponding files.
