package org.example.model;

public class Feedback {

    private String introduccion;
    private String desarrollo;
    private String cierre;

    public Feedback(String introduccion, String desarrollo, String cierre) {
        this.introduccion = introduccion;
        this.desarrollo = desarrollo;
        this.cierre = cierre;
    }

    public String getIntroduccion() { return introduccion; }
    public String getDesarrollo() { return desarrollo; }
    public String getCierre() { return cierre; }

    public void setIntroduccion(String introduccion) { this.introduccion = introduccion; }
    public void setDesarrollo(String desarrollo) { this.desarrollo = desarrollo; }
    public void setCierre(String cierre) { this.cierre = cierre; }

    public String toSingleText() {
        return "INTRODUCCIÓN:\n" + introduccion + "\n\n" +
                "DESARROLLO:\n" + desarrollo + "\n\n" +
                "CIERRE:\n" + cierre;
    }
}