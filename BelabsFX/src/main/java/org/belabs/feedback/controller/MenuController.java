package org.belabs.feedback.controller;

import org.belabs.feedback.BelabsFeedbackApplication.AppNavigator;
import org.belabs.feedback.view.FeedbackView;
import org.belabs.feedback.view.LaboratoryView;
import org.belabs.feedback.view.MenuView;
import org.belabs.feedback.view.SkillView;

public class MenuController {
    private final AppNavigator navigator;
    private final SkillView skillView;
    private final LaboratoryView laboratoryView;
    private final FeedbackView feedbackView;

    public MenuController(AppNavigator navigator, SkillView skillView, LaboratoryView laboratoryView, FeedbackView feedbackView) {
        this.navigator = navigator;
        this.skillView = skillView;
        this.laboratoryView = laboratoryView;
        this.feedbackView = feedbackView;
    }

    public void openSkills() {
        skillView.refreshData();
        navigator.show("Skills", skillView.getScene());
    }

    public void openLaboratories() {
        laboratoryView.refreshData();
        navigator.show("Laboratories", laboratoryView.getScene());
    }

    public void openFeedback() {
        feedbackView.refreshData();
        navigator.show("Feedback", feedbackView.getScene());
    }

    public void backToMenu(MenuView menuView) {
        navigator.show("Belabs Feedback Generator", menuView.getScene());
    }
}
