package com.software.ragp.proyectopsp3.models;

public class CDefectLog {
    private int id;
    private String date;
    private String fixtime;
    private String type;
    private String phaseI;
    private String phaseR;
    private String comments;
    private int proyecto;

    public CDefectLog() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFixtime() {
        return fixtime;
    }

    public void setFixtime(String fixtime) {
        this.fixtime = fixtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhaseI() {
        return phaseI;
    }

    public void setPhaseI(String phaseI) {
        this.phaseI = phaseI;
    }

    public String getPhaseR() {
        return phaseR;
    }

    public void setPhaseR(String phaseR) {
        this.phaseR = phaseR;
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
