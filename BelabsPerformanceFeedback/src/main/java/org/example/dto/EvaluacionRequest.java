package org.example.dto;

public class EvaluacionRequest {


        int liderazgo;
        int comunicacion;
        int tiempo;
        int resolucionProblemas;
        String email;

    public EvaluacionRequest(int liderazgo, int comunicacion, int tiempo, int resolucionProblemas, String email) {
        this.liderazgo = liderazgo;
        this.comunicacion = comunicacion;
        this.tiempo = tiempo;
        this.resolucionProblemas = resolucionProblemas;
        this.email = email;
    }

    public int getLiderazgo() {
        return liderazgo;
    }

    public int getComunicacion() {
        return comunicacion;
    }

    public int getTiempo() {
        return tiempo;
    }

    public int getResolucionProblemas() {
        return resolucionProblemas;
    }

    public String getEmail() {
        return email;
    }


}

