package org.example.Controller;

import org.example.Service.AtribuiriService;

import java.util.Scanner;

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
        // TODO: Implement filtering logic
        System.out.println("Filter drivers by status - Not yet implemented");
    }
}
