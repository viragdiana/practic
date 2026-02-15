package org.example;

import org.example.Controller.ConsoleController;
import org.example.Repository.FahrerRepository;
import org.example.Repository.RennenEreignisRepository;
import org.example.Repository.StrafeRepository;
import org.example.Service.AtribuiriService;

public class Main {
    public static void main(String[] args) {
        FahrerRepository fahrerRepository = new FahrerRepository("drivers.json");
        RennenEreignisRepository rennenereignisRepository = new RennenEreignisRepository("events.json");
        StrafeRepository strafeRepository = new StrafeRepository("penalties.json");

        AtribuiriService atribuiriService = new AtribuiriService(fahrerRepository, rennenereignisRepository, strafeRepository);

        ConsoleController controller = new ConsoleController(atribuiriService);
        controller.start();
    }
}
