package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.PriorityQueue;

public class PrintInfo {
    private String outputPath;
    private final Map<String, Runway<Airplane>> runways;
    private final Map<String, Airplane> airplanesRegistry;

    public PrintInfo(String outputPath, Map<String, Runway<Airplane>> runways, Map<String, Airplane> airplanesRegistry) {
        this.outputPath = outputPath;
        this.runways = runways;
        this.airplanesRegistry = airplanesRegistry;
    }

    public void flightInfo(String[] parts) {
        //impartirea argumenteleor.
        String timestamp = parts[0].trim();
        String flightID = parts[2].trim();

        Airplane airplane = airplanesRegistry.get(flightID);
        //am folosit datetimeformatter pentru ca nu-mi se printau secundele in fisier.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String desiredTimeWithSeconds = airplane.getDesiredTime().format(formatter);
        String realTimeWithSeconds = "";

        if (airplane.getRealTime() != null) {
            realTimeWithSeconds = airplane.getRealTime().format(formatter);
        }

        String airplaneType = "";
        if (airplane instanceof WideBodyAirplane) {
            airplaneType = "Wide Body";
        } else if (airplane instanceof NarrowBodyAirplane) {
            airplaneType = "Narrow Body";
        }
        //formatarea dupa outputul cerut.
        StringBuilder flightInfo = new StringBuilder();
        flightInfo.append(timestamp).append(" | ");
        flightInfo.append(airplaneType).append(" - ");
        flightInfo.append(airplane.getModel()).append(" - ");
        flightInfo.append(airplane.getId()).append(" - ");
        flightInfo.append(airplane.getDeparture()).append(" - ");
        flightInfo.append(airplane.getDestination()).append(" - ");
        flightInfo.append(airplane.getFlightStatus()).append(" - ");
        flightInfo.append(desiredTimeWithSeconds);
        if (!realTimeWithSeconds.isEmpty()) {
            flightInfo.append(" - ").append(realTimeWithSeconds);
        }
        //scrierea in fisier
        try (FileWriter fileWriter = new FileWriter(outputPath + "/flight_info.out", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(flightInfo.toString());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void runwayInfo(String[] parts) {
        //impartirea argumentelor
        String timestamp = parts[0].trim();
        String runwayID = parts[2].trim();

        Runway<Airplane> runway = runways.get(runwayID);
        //formatarea numelui fisierului.
        String filename = "runway_info_" + runwayID + "_" + timestamp.replace(":", "-") + ".out";
        //scrierea in fisier
        try (FileWriter fileWriter = new FileWriter(outputPath + "/" + filename);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            LocalTime currentTime = LocalTime.parse(timestamp);
            //determinarea starii pistei.
            String status = "FREE";
            if (runway.isBlocked(currentTime)) {
                status = "OCCUPIED";
            }
            printWriter.println(runwayID + " - " + status);
            //o copie temporara a avioanelor, pentru a nu modifica coada originala.
            PriorityQueue<Airplane> tempQueue = new PriorityQueue<>(runway.getAirplanes());
            while (!tempQueue.isEmpty()) {
                Airplane airplane = tempQueue.poll();
                printWriter.println(airplane.toString());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

