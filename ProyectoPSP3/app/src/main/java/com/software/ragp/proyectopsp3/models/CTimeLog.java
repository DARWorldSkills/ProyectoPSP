package com.software.ragp.proyectopsp3.models;

public class CTimeLog {
    private int id;
    private String phase;
    private String start;
    private String interrupcion;
    private String stop;
    private String delta;
    private String comments;
    private int proyecto;

    public CTimeLog() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getInterrupcion() {
        return interrupcion;
    }

    public void setInterrupcion(String interrupcion) {
        this.interrupcion = interrupcion;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getDelta() {
        return delta;
    }

    public void setDelta(String delta) {
        this.delta = delta;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getProyecto() {
        return proyecto;
    }

    public void setProyecto(int proyecto) {
        this.proyecto = proyecto;
    }
}
