package org.example;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AirplaneManager {
    //stocheaza avioanele intr-o colectie unde cheia e flight id, iar valoarea este avionul.
    private final Map<String, Airplane> airplanesRegistry;

    //initiaza colectia ca fiind goala
    public AirplaneManager() {
        this.airplanesRegistry = new HashMap<>();
    }

    public void allocatePlane(RunwayManager runwayManager, String airplaneType, String model, String flightID,
                              String departure, String destination, String desiredTime, String runwayID,
                              boolean isEmergency) throws CommandManager.IncorrectRunwayException {
        //gasim pista dupa id.
        Runway<Airplane> runway = runwayManager.findRunwayById(runwayID);
        //determinam statusul zborului in functie de utilizarea pistei
        FlightStatus flightStatus;
        if (runway.getUtilization().equals("takeoff")) {
            //pista e de decolare -> locul de plecare trebuie sa fie bucuresti
            if (!departure.equals("Bucharest")) {
                throw new CommandManager.IncorrectRunwayException();
            }
            flightStatus = FlightStatus.WAITING_FOR_TAKEOFF;
        } else if (runway.getUtilization().equals("landing")) {
            //pista e de aterizare -> destinatia trebuie sa fie bucuresti
            if (!destination.equals("Bucharest")) {
                throw new CommandManager.IncorrectRunwayException();
            }
            flightStatus = FlightStatus.WAITING_FOR_LANDING;
        } else {
            throw new CommandManager.IncorrectRunwayException();
        }
        //alocam avionul
        Airplane airplane = null;
        //in functie de tipul sau.
        if (airplaneType.equals("wide body")) {
            airplane = new WideBodyAirplane(model, flightID, departure, destination,
                    LocalTime.parse(desiredTime), null, flightStatus, isEmergency);
        } else if (airplaneType.equals("narrow body")) {
            airplane = new NarrowBodyAirplane(model, flightID, departure, destination,
                    LocalTime.parse(desiredTime), null, flightStatus, isEmergency);
        }
        //adaugam avionul la pista
        runway.addAirplane(airplane);
        //adaugam avionul in registru.
        airplanesRegistry.put(flightID, airplane);
    }

    public Map<String, Airplane> getAirplanesRegistry() {
        return airplanesRegistry;
    }
}
