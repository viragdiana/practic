package org.example.Model;

public class RennenEreignis {
    private Integer id;
    private Integer fahrerId;
    private EreignisTyp typ;
    private Integer basePoints;
    private Integer lap;

    public RennenEreignis() {}

    public RennenEreignis(Integer id, Integer fahrerId, EreignisTyp typ, Integer basePoints, Integer lap) {
        this.id = id;
        this.fahrerId = fahrerId;
        this.typ = typ;
        this.basePoints = basePoints;
        this.lap = lap;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public String toString() {
        return "RennenEreignis{id=" + id + ", fahrerId=" + fahrerId + ", typ=" + typ + ", basePoints=" + basePoints + ", lap=" + lap + "}";
    }
}
