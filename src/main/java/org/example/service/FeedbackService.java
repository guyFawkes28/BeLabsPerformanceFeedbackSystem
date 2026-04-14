package org.example.service;

import org.example.model.Feedback;
import org.example.repository.FeedbackRepository;

public class FeedbackService {

    private final FeedbackRepository repository;

    public FeedbackService(FeedbackRepository repository) {
        this.repository = repository;
    }

    public Feedback editarSeccion(Feedback feedback, String seccion, String nuevoTexto) {

        switch (seccion.toLowerCase()) {
            case "introduccion":
                feedback.setIntroduccion(nuevoTexto);
                break;
            case "desarrollo":
                feedback.setDesarrollo(nuevoTexto);
                break;
            case "cierre":
                feedback.setCierre(nuevoTexto);
                break;
            default:
                throw new IllegalArgumentException("Sección inválida");
        }

        repository.guardar(feedback);
        return feedback;
    }
}