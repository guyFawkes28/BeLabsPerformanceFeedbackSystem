package org.example.service;

import org.example.dto.EvaluacionRequest;
import org.example.model.Feedback;

public interface FeedbackGeneratorPort {
    Feedback generarFeedback(EvaluacionRequest request);
}
