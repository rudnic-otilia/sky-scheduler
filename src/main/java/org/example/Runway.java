package org.example;

import java.time.LocalTime;
import java.util.PriorityQueue;
import java.util.Comparator;

//clasa runway este generica, T extinde airplane si ar trebui sa fie de tipul narrow Body sau wide body.
public class Runway<T extends Airplane> {
    private String id;
    private PriorityQueue<T> airplanes;
    private String utilization;
    private LocalTime unblockTime;
    private String airplaneType;

    public Runway(String id, String utilization,Comparator<T> comparator, String airplaneType, LocalTime unblockTime) {
        this.id = id;
        this.utilization = utilization;
        this.airplanes = new PriorityQueue<>(comparator); //avioanele sunt sortate dupa o anumita regula.
        this.airplaneType = airplaneType;
        //timpul pana la care pista ramane blocata (dupa o manevra).
        this.unblockTime = unblockTime;
    }

    public String getUtilization() {
        return utilization;
    }

    //prioritizeaza avioanele dupa:
    public static class AirplaneComparator<T extends Airplane> implements Comparator<T> {
        public int compare(T airplane1, T airplane2) {
            //statusul zborului: aterizarea are prioritate mai mare ca decolarea
            //aterizarea verificare urgentei:
            if (airplane1.getFlightStatus() == FlightStatus.WAITING_FOR_LANDING &&
                    airplane2.getFlightStatus() == FlightStatus.WAITING_FOR_LANDING) {
                if (airplane1.isEmergency() && !airplane2.isEmergency()) {
                    return -1; // airplane1 are prioritate
                } else if (!airplane1.isEmergency() && airplane2.isEmergency()) {
                    return 1; // airplane2 are prioritate
                }
                //daca conditiile de mai sus sunt egale atunci avioanele cu timpul dorit mai mic au prioritate.
                return airplane1.getDesiredTime().compareTo(airplane2.getDesiredTime());
            }
            //decolarea (se face crescator dupa timpul dorit)
            if (airplane1.getFlightStatus() == FlightStatus.WAITING_FOR_TAKEOFF &&
                    airplane2.getFlightStatus() == FlightStatus.WAITING_FOR_TAKEOFF) {
                return airplane1.getDesiredTime().compareTo(airplane2.getDesiredTime());
            }
            //Verifica daca doar unul dintre avioane asteapta aterizare
            if (airplane1.getFlightStatus() == FlightStatus.WAITING_FOR_LANDING) {
                return -1;// airplane1 are prioritate
            } else if (airplane2.getFlightStatus() == FlightStatus.WAITING_FOR_LANDING) {
                return 1;// airplane2 are prioritate
            }

            return 0;
        }
    }


    public void addAirplane(T airplane) {
        airplanes.add(airplane);
    }
    //scoate avionul cu prioritate din coada
    public T getNextAirplane() {
        return airplanes.poll();
    }

    public PriorityQueue<T> getAirplanes() {
        return airplanes;
    }


    public String toString() {
        String result = id + "\n";

        for (T airplane : airplanes) {
            result += airplane.toString() + "\n";
        }
        return result;
    }

    //metode pt a bloca si debloca pista dupa ce s-a efectuat o maneuver
    public void blockRunway(LocalTime timestamp, int minutes) {
        this.unblockTime = timestamp.plusMinutes(minutes);
    }

    public boolean isBlocked(LocalTime timestamp) {
        return unblockTime != null && !timestamp.isAfter(unblockTime);
    }

}
