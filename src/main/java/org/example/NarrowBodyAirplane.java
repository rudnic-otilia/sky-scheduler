package org.example;

import java.time.LocalTime;

public class NarrowBodyAirplane extends Airplane {
    public NarrowBodyAirplane(String model, String id, String departure, String destination, LocalTime desiredTime,
                            LocalTime realTime, FlightStatus flightStatus, boolean isEmergency) {
        super(model, id, departure, destination, desiredTime, realTime, flightStatus, isEmergency);
    }

    public String toString() {
        return "Narrow Body - " + super.toString();
    }
}
