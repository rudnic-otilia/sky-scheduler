package org.example;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;


public class CommandManager {

    private final String outputPath;
    private final RunwayManager runwayManager;
    private final AirplaneManager airplaneManager;
    private final PrintInfo printInfo;

    public CommandManager(String outputPath) {
        this.outputPath = outputPath;
        this.runwayManager = new RunwayManager();
        this.airplaneManager = new AirplaneManager();
        this.printInfo = new PrintInfo(outputPath, runwayManager.getRunways(), airplaneManager.getAirplanesRegistry());
    }

    public static class UnavailableRunwayException extends RuntimeException {
        public UnavailableRunwayException() {
            super("The chosen runway for maneuver is currently occupied");
        }
        public UnavailableRunwayException(String message) {
            super(message);
        }
    }

    public static class IncorrectRunwayException extends RuntimeException {
        public IncorrectRunwayException() {
            super("The chosen runway for allocating the plane is incorrect");
        }
        public IncorrectRunwayException(String message) {
            super(message);
        }
    }

    public void addRunwayInUse(String[] parts) {
        String id = parts[2].trim();
        String utilization = parts[3].trim();
        String airplaneType = parts[4].trim();
        runwayManager.addRunway(id, utilization, airplaneType);
    }

    public void allocatePlane(String[] parts) throws IncorrectRunwayException {
        String airplaneType = parts[2].trim();
        String model = parts[3].trim();
        String flightID = parts[4].trim();
        String departure = parts[5].trim();
        String destination = parts[6].trim();
        String desiredTime = parts[7].trim();
        String runwayID = parts[8].trim();

        // se verifica daca mai exista si parametru de urgenta.
        boolean isEmergency = parts.length > 9 && parts[9].trim().equals("urgent");
        airplaneManager.allocatePlane(runwayManager, airplaneType, model, flightID, departure,
                destination, desiredTime, runwayID, isEmergency);
    }

    public void flightInfo(String[] parts) {
        printInfo.flightInfo(parts);
    }

    public void runwayInfo(String[] parts) {
        printInfo.runwayInfo(parts);
    }

    public void permissionForManeuver(String[] parts) {
        String timestampString = parts[0].trim();
        String runwayID = parts[2].trim();
        LocalTime timestamp = LocalTime.parse(timestampString);
        runwayManager.permissionForManeuver(runwayID, timestamp);
    }
}
