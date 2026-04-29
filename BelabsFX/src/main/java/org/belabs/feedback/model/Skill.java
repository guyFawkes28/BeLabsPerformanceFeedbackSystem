package org.belabs.feedback.model;

public class Skill {
    private int id;
    private String nombre;
    private String alto;
    private String medio;
    private String bajo;

    public Skill() {
    }

    public Skill(int id, String nombre, String alto, String medio, String bajo) {
        this.id = id;
        this.nombre = nombre;
        this.alto = alto;
        this.medio = medio;
        this.bajo = bajo;
    }

    public Skill(String nombre, String alto, String medio, String bajo) {
        this(0, nombre, alto, medio, bajo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlto() {
        return alto;
    }

    public void setAlto(String alto) {
        this.alto = alto;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    public String getBajo() {
        return bajo;
    }

    public void setBajo(String bajo) {
        this.bajo = bajo;
    }

    public String generarFeedback(int valor) {
        if (valor >= 80) {
            return alto;
        }
        if (valor >= 60) {
            return medio;
        }
        return bajo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
