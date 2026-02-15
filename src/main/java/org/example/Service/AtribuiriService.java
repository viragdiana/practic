package org.example.Service;

import org.example.Model.Fahrer;
import org.example.Model.RennenEreignis;
import org.example.Model.Strafe;
import org.example.Repository.FahrerRepository;
import org.example.Repository.RennenEreignisRepository;
import org.example.Repository.StrafeRepository;

import java.util.List;

public class AtribuiriService {

    private final FahrerRepository fahrerRepository;
    private final RennenEreignisRepository rennenereignisRepository;
    private final StrafeRepository strafeRepository;

    public AtribuiriService(FahrerRepository fahrerRepository,
                            RennenEreignisRepository rennenereignisRepository,
                            StrafeRepository strafeRepository) {
        this.fahrerRepository = fahrerRepository;
        this.rennenereignisRepository = rennenereignisRepository;
        this.strafeRepository = strafeRepository;
    }

    public List<Fahrer> getAllFahrer() {
        return fahrerRepository.findAll();
    }

    public List<RennenEreignis> getAllRennenEreignisse() {
        return rennenereignisRepository.findAll();
    }

    public List<Strafe> getAllStrafen() {
        return strafeRepository.findAll();
    }
}
