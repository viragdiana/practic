package org.example.Controller;

import org.example.Model.*;
import org.example.Service.AtribuiriService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
            System.out.println("1. Load and display data");
            System.out.println("2. Filter drivers by team and status");
            System.out.println("3. Sort drivers by skill level");
            System.out.println("4. Write sorted drivers to file");
            System.out.println("5. Calculate computed points");
            System.out.println("6. Show ranking");
            System.out.println("7. Race report");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    loadAndDisplayData();
                    break;
                case "2":
                    filterByTeamAndStatus();
                    break;
                case "3":
                    sortDriversBySkill();
                    break;
                case "4":
                    writeSortedDriversToFile();
                    break;
                case "5":
                    calculateComputedPoints();
                    break;
                case "6":
                    showRanking();
                    break;
                case "7":
                    raceReport();
                    break;
                case "0":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // Task 1: Load and display all data
    private void loadAndDisplayData() {
        List<Fahrer> drivers = atribuiriService.getAllFahrer();
        List<RennenEreignis> events = atribuiriService.getAllRennenEreignisse();
        List<Strafe> penalties = atribuiriService.getAllStrafen();

        System.out.println("Drivers loaded: " + drivers.size());
        System.out.println("Events loaded: " + events.size());
        System.out.println("Penalties loaded: " + penalties.size());
        System.out.println();
        drivers.forEach(System.out::println);
    }

    // Task 2: Filter by team and status ACTIVE
    private void filterByTeamAndStatus() {
        System.out.print("Enter team name: ");
        String team = scanner.nextLine().trim();

        List<Fahrer> filtered = atribuiriService.filterByTeamAndStatus(team);

        if (filtered.isEmpty()) {
            System.out.println("No active drivers found for team " + team);
        } else {
            System.out.println("Active drivers in team " + team + ":");
            filtered.forEach(System.out::println);
        }
    }

    // Task 3: Sort by skillLevel descending, name ascending for ties
    private void sortDriversBySkill() {
        List<Fahrer> sorted = atribuiriService.sortDriversBySkill();
        System.out.println("Drivers sorted by skill level:");
        sorted.forEach(System.out::println);
    }

    // Task 4: Write sorted list to drivers_sorted.txt
    private void writeSortedDriversToFile() {
        List<Fahrer> sorted = atribuiriService.sortDriversBySkill();
        try (PrintWriter writer = new PrintWriter(new FileWriter("drivers_sorted.txt"))) {
            for (Fahrer f : sorted) {
                writer.println(f.toString());
            }
            System.out.println("Sorted drivers written to drivers_sorted.txt");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    // Task 5: Compute points and show first 5 events
    private void calculateComputedPoints() {
        List<RennenEreignis> events = atribuiriService.getAllRennenEreignisse();
        Map<Integer, Fahrer> driverMap = atribuiriService.getAllFahrer().stream()
                .collect(Collectors.toMap(Fahrer::getId, f -> f));

        System.out.println("First 5 events with computed points:");
        events.stream().limit(5).forEach(event -> {
            int computed = atribuiriService.computePoints(event);
            Fahrer driver = driverMap.get(event.getFahrerId());
            String name = driver != null ? driver.getName() : "Unknown";
            System.out.println("  " + name + " | " + event.getTyp() + " | lap " + event.getLap()
                    + " | base=" + event.getBasePoints() + " | computed=" + computed);
        });
    }

    // Task 6: Ranking - top 5, winning team
    private void showRanking() {
        Map<Integer, Integer> totalScores = atribuiriService.calculateTotalScores();
        Map<Integer, Fahrer> driverMap = atribuiriService.getAllFahrer().stream()
                .collect(Collectors.toMap(Fahrer::getId, f -> f));

        List<Map.Entry<Integer, Integer>> ranked = totalScores.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()
                        .thenComparing(entry -> driverMap.containsKey(entry.getKey())
                                ? driverMap.get(entry.getKey()).getName() : ""))
                .collect(Collectors.toList());

        System.out.println("Top 5 Ranking:");
        for (int i = 0; i < Math.min(5, ranked.size()); i++) {
            Map.Entry<Integer, Integer> entry = ranked.get(i);
            Fahrer driver = driverMap.get(entry.getKey());
            String name = driver != null ? driver.getName() + " (" + driver.getTeam() + ")" : "Unknown";
            System.out.println("  " + (i + 1) + ". " + name + " -> " + entry.getValue());
        }

        if (!ranked.isEmpty()) {
            Fahrer winner = driverMap.get(ranked.get(0).getKey());
            if (winner != null) {
                System.out.println("Winning team: " + winner.getTeam());
            }
        }
    }

    // Task 7: Race report - event count per type, sorted descending, written to file
    private void raceReport() {
        Map<EreignisTyp, Long> counts = atribuiriService.countEventsByType();

        List<Map.Entry<EreignisTyp, Long>> sorted = counts.entrySet().stream()
                .sorted(Map.Entry.<EreignisTyp, Long>comparingByValue().reversed())
                .collect(Collectors.toList());

        try (PrintWriter writer = new PrintWriter(new FileWriter("race_report.txt"))) {
            writer.println("=== Race Report ===");
            for (Map.Entry<EreignisTyp, Long> entry : sorted) {
                String line = entry.getKey() + ": " + entry.getValue();
                writer.println(line);
            }
            System.out.println("Race report written to race_report.txt");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
