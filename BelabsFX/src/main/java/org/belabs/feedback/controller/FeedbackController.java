package org.belabs.feedback.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.belabs.feedback.model.Coder;
import org.belabs.feedback.model.Laboratory;
import org.belabs.feedback.model.Skill;
import org.belabs.feedback.repository.CoderRepository;
import org.belabs.feedback.service.EmailService;
import org.belabs.feedback.service.FeedbackService;
import org.belabs.feedback.service.LaboratoryService;
import org.belabs.feedback.util.AlertUtil;
import org.belabs.feedback.view.FeedbackView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FeedbackController {
    private final FeedbackService feedbackService;
    private final EmailService emailService;
    private final LaboratoryService laboratoryService;
    private final CoderRepository coderRepository;
    private FeedbackView view;

    public FeedbackController(FeedbackService feedbackService, EmailService emailService, LaboratoryService laboratoryService, CoderRepository coderRepository) {
        this.feedbackService = feedbackService;
        this.emailService = emailService;
        this.laboratoryService = laboratoryService;
        this.coderRepository = coderRepository;
    }

    public void setView(FeedbackView view) {
        this.view = view;
    }

    public ObservableList<Laboratory> loadLaboratories() {
        ObservableList<Laboratory> laboratories = FXCollections.observableArrayList(laboratoryService.getAllLaboratories());
        if (view != null) {
            view.setLaboratories(laboratories);
        }
        return laboratories;
    }

    public ObservableList<Coder> loadCoders() {
        ObservableList<Coder> coders = FXCollections.observableArrayList(coderRepository.findAll());
        if (view != null) {
            view.setCoders(coders);
        }
        return coders;
    }

    public String handleGenerateFeedback(Coder coder, Laboratory laboratory, List<String> scoreValues) {
        try {
            if (coder == null) {
                throw new IllegalArgumentException("Debe seleccionar un coder.");
            }
            if (laboratory == null) {
                throw new IllegalArgumentException("Debe seleccionar un laboratorio.");
            }
            Map<Skill, Integer> scores = new LinkedHashMap<>();
            List<Skill> skills = laboratory.getSkills();
            for (int index = 0; index < skills.size(); index++) {
                scores.put(skills.get(index), Integer.parseInt(scoreValues.get(index)));
            }
            String feedback = feedbackService.generarFeedback(laboratory, scores);
            view.setGeneratedFeedback(feedback);
            return feedback;
        } catch (Exception exception) {
            AlertUtil.showError("Feedback", exception.getMessage());
            return null;
        }
    }

    public void handleSendEmail(Coder coder, String feedback) {
        try {
            if (coder == null) {
                throw new IllegalArgumentException("Debe seleccionar un coder.");
            }
            if (feedback == null || feedback.isBlank()) {
                throw new IllegalArgumentException("Primero genere el feedback.");
            }
            emailService.sendEmail(coder.getEmail(), "Belabs Feedback", feedback);
            AlertUtil.showInfo("Correo", "Feedback enviado correctamente.");
        } catch (Exception exception) {
            AlertUtil.showError("Correo", exception.getMessage());
        }
    }
}
