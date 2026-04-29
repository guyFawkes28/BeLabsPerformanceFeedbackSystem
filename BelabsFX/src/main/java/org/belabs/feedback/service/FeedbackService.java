package org.belabs.feedback.service;

import org.belabs.feedback.model.Laboratory;
import org.belabs.feedback.model.Skill;

import java.util.Map;
import java.util.StringJoiner;

public class FeedbackService {
    public String generarFeedback(Laboratory laboratory, Map<Skill, Integer> notas) {
        if (laboratory == null) {
            throw new IllegalArgumentException("Debe seleccionar un laboratorio.");
        }
        if (laboratory.getSkills().size() != 4) {
            throw new IllegalArgumentException("El laboratorio debe tener exactamente 4 habilidades.");
        }

        StringJoiner joiner = new StringJoiner(System.lineSeparator() + System.lineSeparator());
        for (Skill skill : laboratory.getSkills()) {
            Integer nota = notas.get(skill);
            if (nota == null || nota < 1 || nota > 100) {
                throw new IllegalArgumentException("Cada nota debe estar entre 1 y 100.");
            }
            joiner.add(skill.getNombre() + ": " + skill.generarFeedback(nota));
        }
        return joiner.toString();
    }
}
