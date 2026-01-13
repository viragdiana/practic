package org.example.Service;


import org.example.Model.RennenEreignis;
import org.example.Model.Fahrer;
import org.example.Repository.IRepository;

import java.util.Comparator;
import java.util.List;

public class AtribuiriService {

    private final IRepository<Fahrer, Integer> fahrerRepository;
    private final IRepository<RennenEreignis, Integer> rennenereignisRepository;

    public AtribuiriService(IRepository<Fahrer, Integer> fahrerRepository,
                            IRepository<RennenEreignis, Integer> rennenereignisRepository) {
        this.fahrerRepository = fahrerRepository;
        this.rennenereignisRepository = rennenereignisRepository;
    }

    public RennenEreignis getRennenEreignis(Integer id) {
        return rennenereignisRepository.findById(id);
    }

    public void addRennenEreignisse(RennenEreignis medikamente) {
        rennenereignisRepository.save(medikamente);
    }

    public void addRennenEreignisse(Integer id) {
        rennenereignisRepository.delete(id);
    }

    public List<RennenEreignis> getAllRennenEreignisse() {
        return  rennenereignisRepository.findAll();
    }

    public List<RennenEreignis> getrennenEreignisse() {
        return rennenereignisRepository.findAll();
    }

    public void addFahrer(Fahrer fahrer) {
        fahrerRepository.save(fahrer);
    }

    public List<Fahrer> getAllFahrer() {
        return fahrerRepository.findAll();
    }

////
//    public List<Fahrer> getPatientByDiagnose(String diagnose) {
//        return patientRepository.findAll().stream()
//                .filter(patient -> patient.getDiagnose().equalsIgnoreCase(diagnose))
//                .toList();
//    }
//
//
//    public List<Patient> filterByKrankheit(String krankheit) {
//        return patientRepository.findAll().stream()
//                .filter(p -> p.getMedikamente().stream()
//                        .anyMatch(b-> b.getKrankheit().equalsIgnoreCase(krankheit)))
//                .toList();
//    }

    //alle Medikamente sortieren, die einem
    //bestimmten Patienten verschrieben wurden. Sowohl der Patient als auch der
    //Sortiermodus (aufsteigend oder absteigend nach Preis) werden vom Benutzer
    //angegeben.


//
//    public List<Medikamente> sortedByMedikamenteAndPrice (String patientName, String modus) {
//        List<Medikamente> medikamentes= patientRepository.findAll().stream()
//                .filter(p -> p.getName().equalsIgnoreCase(patientName))
//                .flatMap(m -> m.getMedikamente().stream())
//                .toList();
//
//        if(modus.equalsIgnoreCase("aufsteigend")) {
//            return medikamentes.stream()
//                    .sorted(Comparator.comparingDouble(Medikamente::getPreis))
//                    .toList();
//        } else if (modus.equalsIgnoreCase("absteigend")) {
//            return medikamentes.stream()
//                    .sorted(Comparator.comparingDouble(Medikamente::getPreis).reversed())
//                    .toList().reversed();
//        } else {
//            throw new IllegalArgumentException("Ungültiger Sortiermodus: " + modus);
//        }
//    }

}
