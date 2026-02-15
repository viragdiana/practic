package org.example.Controller;

import org.example.Model.*;
import org.example.Service.AtribuiriService;

import java.util.*;
import java.util.stream.Collectors;

public class ConsoleController {

    private final AtribuiriService atribuiriService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleController(AtribuiriService atribuiriService) {
        this.atribuiriService = atribuiriService;
    }

    public void start() {
        while (true) {
            System.out.println("\n=== Racing Management System ===");
            System.out.println("1. Filter drivers by status");
            System.out.println("2. Show events for a driver");
            System.out.println("3. Show penalties for a driver");
            System.out.println("4. Filter events by type");
            System.out.println("5. Calculate total points per driver");
            System.out.println("6. Show drivers sorted by total penalty seconds");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    filterDriversByStatus();
                    break;
                case "2":
                    showEventsForDriver();
                    break;
                case "3":
                    showPenaltiesForDriver();
                    break;
                case "4":
                    filterEventsByType();
                    break;
                case "5":
                    calculateTotalPointsPerDriver();
                    break;
                case "6":
                    showDriversSortedByPenaltySeconds();
                    break;
                case "0":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void filterDriversByStatus() {
        System.out.println("Available statuses: ACTIVE, DNF");
        System.out.print("Enter status: ");
        String input = scanner.nextLine().trim().toUpperCase();

        try {
            FahrerStatus status = FahrerStatus.valueOf(input);
            var filtered = atribuiriService.getAllFahrer().stream()
                    .filter(f -> f.getStatus() == status)
                    .collect(Collectors.toList());

            if (filtered.isEmpty()) {
                System.out.println("No drivers found with status " + status);
            } else {
                System.out.println("Drivers with status " + status + ":");
                filtered.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status! Please enter ACTIVE or DNF.");
        }
    }

    private void showEventsForDriver() {
        List<Fahrer> drivers = atribuiriService.getAllFahrer();
        System.out.println("Available drivers:");
        drivers.forEach(f -> System.out.println("  " + f.getId() + " - " + f.getName()));
        System.out.print("Enter driver ID: ");
        String input = scanner.nextLine().trim();

        try {
            int driverId = Integer.parseInt(input);
            Fahrer driver = drivers.stream()
                    .filter(f -> f.getId().equals(driverId))
                    .findFirst()
                    .orElse(null);

            if (driver == null) {
                System.out.println("Driver not found with ID " + driverId);
                return;
            }

            var events = atribuiriService.getAllRennenEreignisse().stream()
                    .filter(e -> e.getFahrerId().equals(driverId))
                    .collect(Collectors.toList());

            if (events.isEmpty()) {
                System.out.println("No events found for " + driver.getName());
            } else {
                System.out.println("Events for " + driver.getName() + " (" + driver.getTeam() + "):");
                events.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID! Please enter a number.");
        }
    }

    private void showPenaltiesForDriver() {
        List<Fahrer> drivers = atribuiriService.getAllFahrer();
        System.out.println("Available drivers:");
        drivers.forEach(f -> System.out.println("  " + f.getId() + " - " + f.getName()));
        System.out.print("Enter driver ID: ");
        String input = scanner.nextLine().trim();

        try {
            int driverId = Integer.parseInt(input);
            Fahrer driver = drivers.stream()
                    .filter(f -> f.getId().equals(driverId))
                    .findFirst()
                    .orElse(null);

            if (driver == null) {
                System.out.println("Driver not found with ID " + driverId);
                return;
            }

            var penalties = atribuiriService.getAllStrafen().stream()
                    .filter(s -> s.getFahrerId().equals(driverId))
                    .collect(Collectors.toList());

            if (penalties.isEmpty()) {
                System.out.println("No penalties found for " + driver.getName());
            } else {
                System.out.println("Penalties for " + driver.getName() + " (" + driver.getTeam() + "):");
                penalties.forEach(System.out::println);
                int totalSeconds = penalties.stream().mapToInt(Strafe::getSeconds).sum();
                System.out.println("Total penalty time: " + totalSeconds + " seconds");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID! Please enter a number.");
        }
    }

    private void filterEventsByType() {
        System.out.println("Available event types: OVERTAKE, PIT_STOP, FASTEST_LAP, COLLISION, TRACK_LIMITS");
        System.out.print("Enter event type: ");
        String input = scanner.nextLine().trim().toUpperCase();

        try {
            EreignisTyp typ = EreignisTyp.valueOf(input);
            var filtered = atribuiriService.getAllRennenEreignisse().stream()
                    .filter(e -> e.getTyp() == typ)
                    .collect(Collectors.toList());

            Map<Integer, Fahrer> driverMap = atribuiriService.getAllFahrer().stream()
                    .collect(Collectors.toMap(Fahrer::getId, f -> f));

            if (filtered.isEmpty()) {
                System.out.println("No events found with type " + typ);
            } else {
                System.out.println("Events of type " + typ + ":");
                for (RennenEreignis event : filtered) {
                    Fahrer driver = driverMap.get(event.getFahrerId());
                    String driverName = driver != null ? driver.getName() : "Unknown";
                    System.out.println("  Lap " + event.getLap() + " - " + driverName + " (points: " + event.getBasePoints() + ")");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid event type!");
        }
    }

    private void calculateTotalPointsPerDriver() {
        Map<Integer, Fahrer> driverMap = atribuiriService.getAllFahrer().stream()
                .collect(Collectors.toMap(Fahrer::getId, f -> f));

        Map<Integer, Integer> pointsByDriver = atribuiriService.getAllRennenEreignisse().stream()
                .collect(Collectors.groupingBy(
                        RennenEreignis::getFahrerId,
                        Collectors.summingInt(e -> e.getBasePoints() * driverMap.getOrDefault(e.getFahrerId(), new Fahrer()).getSkillLevel())
                ));

        System.out.println("Total points per driver (basePoints * skillLevel):");
        pointsByDriver.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .forEach(entry -> {
                    Fahrer driver = driverMap.get(entry.getKey());
                    String name = driver != null ? driver.getName() : "Unknown";
                    System.out.println("  " + name + ": " + entry.getValue() + " points");
                });
    }

    private void showDriversSortedByPenaltySeconds() {
        Map<Integer, Fahrer> driverMap = atribuiriService.getAllFahrer().stream()
                .collect(Collectors.toMap(Fahrer::getId, f -> f));

        Map<Integer, Integer> penaltyByDriver = atribuiriService.getAllStrafen().stream()
                .collect(Collectors.groupingBy(
                        Strafe::getFahrerId,
                        Collectors.summingInt(Strafe::getSeconds)
                ));

        if (penaltyByDriver.isEmpty()) {
            System.out.println("No penalties recorded.");
            return;
        }

        System.out.println("Drivers sorted by total penalty seconds (descending):");
        penaltyByDriver.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .forEach(entry -> {
                    Fahrer driver = driverMap.get(entry.getKey());
                    String name = driver != null ? driver.getName() : "Unknown";
                    System.out.println("  " + name + ": " + entry.getValue() + "s penalty");
                });
    }
}
