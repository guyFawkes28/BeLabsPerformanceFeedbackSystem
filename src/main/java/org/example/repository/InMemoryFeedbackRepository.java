package org.example.repository;

import org.example.model.Feedback;

public class InMemoryFeedbackRepository implements FeedbackRepository {

    private Feedback feedback;

    @Override
    public void guardar(Feedback feedback) {
        this.feedback = feedback;
    }

    @Override
    public Feedback obtener() {
        return feedback;
    }
}