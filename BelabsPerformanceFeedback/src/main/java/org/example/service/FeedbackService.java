package org.example.service;

import org.example.dto.EvaluacionRequest;
import org.example.model.Feedback;

public class FeedbackService implements  FeedbackGeneratorPort{

    private String clasificar(int valor) {
        if (valor >= 8) return "Alto";
        if (valor >= 5) return "Medio";
        return "Bajo";
    }

    @Override
    public Feedback generarFeedback(EvaluacionRequest request) {

        if (request == null) { return null; }

        String liderazgo = clasificar(request.getLiderazgo());
        String comunicacion = clasificar(request.getComunicacion());
        String tiempo = clasificar(request.getTiempo());
        String resolucion = clasificar(request.getResolucionProblemas());

        String desarrollo = "Liderazgo: " + liderazgo +
                ", Comunicación: " + comunicacion +
                ", Gestión del tiempo: " + tiempo +
                ", Resolución de problemas: " + resolucion;

        return new Feedback("", desarrollo, "");
    }

}
