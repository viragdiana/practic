package org.example;

import org.example.Controller.ConsoleController;

import org.example.Model.RennenEreignis;
import org.example.Model.Fahrer;
import org.example.Repository.IRepository;

import org.example.Repository.RennenEreignisRepository;
import org.example.Repository.FahrerRepository;
import org.example.Service.AtribuiriService;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        IRepository<Fahrer,Integer> fahrerRepository = new FahrerRepository("drivers.json");
        IRepository<RennenEreignis, Integer> rennenereignisRepository = new RennenEreignisRepository("events.json");

        AtribuiriService atribuiriService = new AtribuiriService(fahrerRepository, rennenereignisRepository);

        ConsoleController controller = new ConsoleController ( atribuiriService);
        controller.start();
    }
}