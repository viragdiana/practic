package org.example.Controller;

import org.example.Model.FahrerStatus;
import org.example.Service.AtribuiriService;

import java.util.Scanner;
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
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    filterDriversByStatus();
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
}
