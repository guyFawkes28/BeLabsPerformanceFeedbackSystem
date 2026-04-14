package org.example.repository;

import org.example.model.Feedback;

public interface FeedbackRepository {
    void guardar(Feedback feedback);
    Feedback obtener();
}