package org.belabs.feedback.view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.belabs.feedback.controller.FeedbackController;
import org.belabs.feedback.model.Coder;
import org.belabs.feedback.model.Laboratory;
import org.belabs.feedback.model.Skill;

import java.util.ArrayList;
import java.util.List;

public class FeedbackView {
    private final Scene scene;
    private final FeedbackController controller;
    private final ComboBox<Coder> coderComboBox = new ComboBox<>();
    private final ComboBox<Laboratory> laboratoryComboBox = new ComboBox<>();
    private final Label[] skillLabels = new Label[4];
    private final TextField[] scoreFields = new TextField[4];
    private final TextArea feedbackArea = new TextArea();

    public FeedbackView(FeedbackController controller, Runnable onBack) {
        this.controller = controller;
        GridPane scoreGrid = new GridPane();
        scoreGrid.setHgap(10);
        scoreGrid.setVgap(8);

        for (int index = 0; index < 4; index++) {
            skillLabels[index] = new Label("Skill " + (index + 1));
            scoreFields[index] = new TextField();
            scoreFields[index].setPromptText("1-100");
            scoreGrid.addRow(index, skillLabels[index], scoreFields[index]);
        }

        laboratoryComboBox.setOnAction(event -> updateSkillLabels(laboratoryComboBox.getValue()));
        feedbackArea.setPrefRowCount(12);

        Button generateButton = new Button("Generar feedback");
        generateButton.setOnAction(event -> controller.handleGenerateFeedback(coderComboBox.getValue(), laboratoryComboBox.getValue(), getScoreValues()));

        Button sendButton = new Button("Enviar correo");
        sendButton.setOnAction(event -> controller.handleSendEmail(coderComboBox.getValue(), feedbackArea.getText()));
        Button backButton = new Button("Volver al menu");
        backButton.setOnAction(event -> onBack.run());

        VBox root = new VBox(10,
                new Label("Coder"), coderComboBox,
                new Label("Laboratorio"), laboratoryComboBox,
                new Label("Notas"), scoreGrid,
                generateButton,
                new Label("Feedback generado"), feedbackArea,
                sendButton,
                backButton
        );
        root.setPadding(new Insets(18));
        this.scene = new Scene(root, 760, 620);
    }

    public void refreshData() {
        controller.loadCoders();
        controller.loadLaboratories();
        feedbackArea.clear();
        updateSkillLabels(laboratoryComboBox.getValue());
    }

    public void setCoders(ObservableList<Coder> coders) {
        coderComboBox.setItems(coders);
    }

    public void setLaboratories(ObservableList<Laboratory> laboratories) {
        laboratoryComboBox.setItems(laboratories);
        if (!laboratories.isEmpty() && laboratoryComboBox.getValue() == null) {
            laboratoryComboBox.getSelectionModel().selectFirst();
            updateSkillLabels(laboratoryComboBox.getValue());
        }
    }

    public void setGeneratedFeedback(String feedback) {
        feedbackArea.setText(feedback);
    }

    public Scene getScene() {
        return scene;
    }

    private List<String> getScoreValues() {
        List<String> values = new ArrayList<>();
        for (TextField scoreField : scoreFields) {
            values.add(scoreField.getText());
        }
        return values;
    }

    private void updateSkillLabels(Laboratory laboratory) {
        List<Skill> skills = laboratory == null ? List.of() : laboratory.getSkills();
        for (int index = 0; index < 4; index++) {
            if (index < skills.size()) {
                skillLabels[index].setText(skills.get(index).getNombre());
            } else {
                skillLabels[index].setText("Skill " + (index + 1));
            }
            scoreFields[index].clear();
        }
    }
}
