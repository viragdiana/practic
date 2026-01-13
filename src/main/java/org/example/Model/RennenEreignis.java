package org.example.Model;

public class RennenEreignis implements HasId<Integer>{
    private Integer id;
    private Integer fahrerId;
    EreignisTyp typ;
    private  Integer basePoints;
    private Integer lap;

    public RennenEreignis() {}

    public RennenEreignis(Integer id, Integer fahrerId, EreignisTyp typ, Integer basePoints, Integer lap) {
        this.id = id;
        this.fahrerId = fahrerId;
        this.typ = typ;
        this.basePoints = basePoints;
        this.lap = lap;
    }

    public Integer getFahrerId() {
        return fahrerId;
    }

    public void setFahrerId(Integer fahrerId) {
        this.fahrerId = fahrerId;
    }

    public EreignisTyp getTyp() {
        return typ;
    }

    public void setTyp(EreignisTyp typ) {
        this.typ = typ;
    }

    public Integer getBasePoints() {
        return basePoints;
    }

    public void setBasePoints(Integer basePoints) {
        this.basePoints = basePoints;
    }

    public Integer getLap() {
        return lap;
    }

    public void setLap(Integer lap) {
        this.lap = lap;
    }

    @Override
    public Integer getId() {
        return 0;
    }

    @Override
    public void setId(Integer integer) {

    }
}
