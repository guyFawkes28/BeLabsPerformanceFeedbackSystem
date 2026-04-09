package org.example.service;

import org.example.model.Feedback;

public class FeedbackFormatter implements FeedbackFormatterPort {

    @Override
    public Feedback formatear(Feedback feedback) {

        feedback.setIntroduccion("Este es un análisis del desempeño del evaluado.");

        feedback.setCierre("Se recomienda continuar fortaleciendo las habilidades evaluadas.");

        return feedback;
    }
}
