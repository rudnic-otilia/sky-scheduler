package org.example;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

enum FlightStatus {
    WAITING_FOR_TAKEOFF,
    DEPARTED,
    WAITING_FOR_LANDING,
    LANDED
}

public class Airplane {
    private String model;
    private String id;
    private String departure;
    private String destination;
    private LocalTime desiredTime;
    private LocalTime realTime;
    private FlightStatus flightStatus;
    private boolean isEmergency;

    public Airplane(String model, String id, String departure, String destination, LocalTime desiredTime,
                    LocalTime realTime, FlightStatus flightStatus, boolean isEmergency) {
        this.model = model;
        this.id = id;
        this.departure = departure;
        this.destination = destination;
        this.desiredTime = desiredTime;
        this.realTime = realTime;
        this.flightStatus = flightStatus;
        this.isEmergency = isEmergency;
    }


    public boolean isEmergency() {
        return isEmergency;
    }

    public String getModel() {
        return model;
    }

    public String getId() {
        return id;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public LocalTime getDesiredTime() {
        return desiredTime;
    }

    public LocalTime getRealTime() {
        return realTime;
    }

    public FlightStatus getFlightStatus() {
        return flightStatus;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDesiredTime(LocalTime desiredTime) {
        this.desiredTime = this.desiredTime;
    }

    public void setFlightStatus(FlightStatus flightStatus) {
        this.flightStatus = flightStatus;
    }

    public void setRealTime(LocalTime realTime) {
        this.realTime = realTime;
    }

    public void setEmergency(boolean isEmergency) {
        this.isEmergency = isEmergency;
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String result = model + " - " + id + " - " + departure + " - " + destination + " - "
                + flightStatus + " - " + desiredTime.format(formatter);
        if (realTime != null) {
            result += " - " + realTime.format(formatter);
        }
        return result;
    }
}
