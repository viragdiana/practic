package org.example.Controller;

import org.example.Model.RennenEreignis;
import org.example.Model.Fahrer;
import org.example.Service.AtribuiriService;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleController {

    private final AtribuiriService atribuiriService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleController( AtribuiriService atribuiriService) {
        this.atribuiriService = atribuiriService;
    }

    public void start() {
        while (true) {

            System.out.println("3.Fahrer anzeigen");
            System.out.println("4.Patient anzeigen");
            System.out.println("5.Patient loschen");
            System.out.println("6.Patient eimnfugen");
            System.out.println("7.getAllKrankenheiten");
            System.out.println("8. getAllFalle");
            System.out.println("9. countFalleProKrankenhaus");
            System.out.println("10. filterByKrankheit");
            System.out.println("11. getPatientByDiagnose");
            System.out.println("12. sortedByMedikamenteAndPrice");
            System.out.println("12. sortedByMedikamenteAndPrice");
            String choice = scanner.nextLine();
            switch (choice) {

                case "3": {
                    System.out.println("Liste der Fahrer:");
                    atribuiriService.getAllFahrer().forEach(System.out::println);
                    break;
                }

                case "4": {   // ✅ FIXED (was "4:")
                    System.out.println("Liste der Fahrer:");
                    // print anzahl der Fahrer

                    atribuiriService.getAllFahrer().forEach(System.out::println);
                    break;
                }

                case "5": {
                    System.out.println("Geben Sie die ID des zu löschenden Patienten ein:");
                    int id = Integer.parseInt(scanner.nextLine());
                    // atribuiriService.deletePatient(id);
                    break;
                }

                case "6": {
                }

                case "7": {

                }

                case "8": {

                }

            }
        }
    }
}
