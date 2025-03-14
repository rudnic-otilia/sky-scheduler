package org.example;

import java.time.LocalTime;

public class WideBodyAirplane extends Airplane {
    public WideBodyAirplane(String model, String id, String departure, String destination, LocalTime desiredTime,
                            LocalTime realTime, FlightStatus flightStatus, boolean isEmergency) {
        super(model, id, departure, destination, desiredTime, realTime, flightStatus, isEmergency);
    }
    public String toString() {
        return "Wide Body - " + super.toString();
    }
}
