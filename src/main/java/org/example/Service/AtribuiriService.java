package org.example.Service;

import org.example.Model.*;
import org.example.Repository.FahrerRepository;
import org.example.Repository.RennenEreignisRepository;
import org.example.Repository.StrafeRepository;

import java.util.*;
import java.util.stream.Collectors;

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

    // Task 2: Filter drivers by team AND status ACTIVE
    public List<Fahrer> filterByTeamAndStatus(String team) {
        return getAllFahrer().stream()
                .filter(f -> f.getTeam().equalsIgnoreCase(team) && f.getStatus() == FahrerStatus.ACTIVE)
                .collect(Collectors.toList());
    }

    // Task 3: Sort drivers by skillLevel descending, then name ascending for ties
    public List<Fahrer> sortDriversBySkill() {
        return getAllFahrer().stream()
                .sorted(Comparator.comparing(Fahrer::getSkillLevel).reversed()
                        .thenComparing(Fahrer::getName))
                .collect(Collectors.toList());
    }

    // Task 5: Compute points for a single event
    public int computePoints(RennenEreignis event) {
        int bp = event.getBasePoints();
        int lap = event.getLap();
        switch (event.getTyp()) {
            case OVERTAKE:
                return bp + 1;
            case FASTEST_LAP:
                return bp + 2 * (lap % 3);
            case TRACK_LIMITS:
                return bp - 5;
            case COLLISION:
                return bp - 10 - lap;
            case PIT_STOP:
                return lap <= 10 ? bp + 2 : bp;
            default:
                return bp;
        }
    }

    // Task 6: Total score per driver = sum(computedPoints) - sum(penalty seconds)
    public Map<Integer, Integer> calculateTotalScores() {
        Map<Integer, Integer> eventPoints = new HashMap<>();
        for (RennenEreignis event : getAllRennenEreignisse()) {
            eventPoints.merge(event.getFahrerId(), computePoints(event), Integer::sum);
        }

        Map<Integer, Integer> penaltySeconds = new HashMap<>();
        for (Strafe strafe : getAllStrafen()) {
            penaltySeconds.merge(strafe.getFahrerId(), strafe.getSeconds(), Integer::sum);
        }

        Map<Integer, Integer> totalScores = new HashMap<>();
        Set<Integer> allDriverIds = new HashSet<>();
        allDriverIds.addAll(eventPoints.keySet());
        allDriverIds.addAll(penaltySeconds.keySet());

        for (Integer id : allDriverIds) {
            int points = eventPoints.getOrDefault(id, 0);
            int penalties = penaltySeconds.getOrDefault(id, 0);
            totalScores.put(id, points - penalties);
        }

        return totalScores;
    }

    // Task 7: Count events per EreignisTyp
    public Map<EreignisTyp, Long> countEventsByType() {
        return getAllRennenEreignisse().stream()
                .collect(Collectors.groupingBy(RennenEreignis::getTyp, Collectors.counting()));
    }
}
