package org.example.Model;

public class Strafe {
    private Integer id;
    private Integer fahrerId;
    private SafeGrund grund;
    private Integer seconds;
    private Integer lap;

    public Strafe() {}

    public Strafe(Integer id, Integer fahrerId, SafeGrund grund, Integer seconds, Integer lap) {
        this.id = id;
        this.fahrerId = fahrerId;
        this.grund = grund;
        this.seconds = seconds;
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

    public SafeGrund getGrund() {
        return grund;
    }

    public void setGrund(SafeGrund grund) {
        this.grund = grund;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public Integer getLap() {
        return lap;
    }

    public void setLap(Integer lap) {
        this.lap = lap;
    }
}
