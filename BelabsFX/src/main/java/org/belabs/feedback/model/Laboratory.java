package org.belabs.feedback.model;

import java.util.ArrayList;
import java.util.List;

public class Laboratory {
    private int id;
    private String nombre;
    private List<Skill> skills = new ArrayList<>();

    public Laboratory() {
    }

    public Laboratory(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Laboratory(int id, String nombre, List<Skill> skills) {
        this.id = id;
        this.nombre = nombre;
        this.skills = skills;
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

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
