package org.example;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class RunwayManager {
    private final Map<String, Runway<Airplane>> runways;

    public RunwayManager() {
        this.runways = new HashMap<>();
    }
// initiaza pista.
    public void addRunway(String id, String utilization, String airplaneType) {
        LocalTime unblockTime = null; //e null pt ca maneuver inca nu e folosit
        Runway<Airplane> runway = new Runway<>(id, utilization, new Runway.AirplaneComparator<>(), airplaneType, unblockTime);
        runways.put(id, runway);
    }

    public Runway<Airplane> findRunwayById(String id) {
        return runways.get(id);
    }

    public boolean isRunwayBlocked(String id, LocalTime timestamp) {
        Runway<Airplane> runway = findRunwayById(id);
        return runway != null && runway.isBlocked(timestamp);
    }

    public Map<String, Runway<Airplane>> getRunways() {
        return runways;
    }

    public void permissionForManeuver(String runwayID, LocalTime timestamp) {
        //cauta psita necesara
        Runway<Airplane> runway = findRunwayById(runwayID);
        //daca pista este blocata se arunca o exceptie.
        if (runway.isBlocked(timestamp)) {
            throw new CommandManager.UnavailableRunwayException();
        }
        //ia avionul cu prioritatea ce mai mare.
        Airplane airplane = runway.getNextAirplane();

        //se verifica statusul avionului
        if (airplane.getFlightStatus() == FlightStatus.WAITING_FOR_TAKEOFF) {
            airplane.setFlightStatus(FlightStatus.DEPARTED); //i se seteaza statusul ca a decolat.
            airplane.setRealTime(timestamp); // i se seteaza si timestampul real.
        } else if (airplane.getFlightStatus() == FlightStatus.WAITING_FOR_LANDING) { //la fel si la aterizare
            airplane.setFlightStatus(FlightStatus.LANDED);
            airplane.setRealTime(timestamp);
        }
        //s-a efectuat maneuver si acum se seteaza timpul de blocare, si blocarea pistei.
        int blockTime;
        if (airplane.getFlightStatus() == FlightStatus.DEPARTED) {
            blockTime = 5;
        } else {
            blockTime = 10;
        }
        runway.blockRunway(timestamp, blockTime);
    }
}
