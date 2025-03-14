package org.example;

import java.io.*;
import java.util.Scanner;

public class Main {
    private static CommandManager commandManager;

    public static void main(String[] args) {
        String testName = args[0];
        String inputPath = "src/main/resources/" + testName + "/input.in";
        String outputPath = "src/main/resources/" + testName;

        commandManager = new CommandManager(outputPath);

        try {
            //deschide fisierul pentru citire.
            File inputFile = new File(inputPath);
            Scanner scanner = new Scanner(inputFile);
            //citirea comenzii.
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                processCommand(command, outputPath);
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void processCommand(String command, String outputPath) {
        if (command == null || command.trim().isEmpty()) {
            return;
        }
        String[] parts = command.split(" - ");
        String commandType = parts[1];

        try {
            switch (commandType) {
                case "add_runway_in_use":
                    commandManager.addRunwayInUse(parts);
                    break;
                case "allocate_plane":
                    commandManager.allocatePlane(parts);
                    break;
                case "flight_info":
                    commandManager.flightInfo(parts);
                    break;
                case "exit":
                    return;
                case "runway_info":
                    commandManager.runwayInfo(parts);
                    break;
                case "permission_for_maneuver":
                    commandManager.permissionForManeuver(parts);
                    break;
            }
        } catch (CommandManager.IncorrectRunwayException | CommandManager.UnavailableRunwayException e) {
            fileException(e, outputPath, parts[0]);
        }
    }
//scrierea exceptiilor in fisier.
    private static void fileException(Exception e, String outputPath, String timestamp) {
        String exceptionMessage = timestamp + " | " + e.getMessage();
        try (FileWriter fileWriter = new FileWriter(outputPath + "/board_exceptions.out", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(exceptionMessage);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
}
