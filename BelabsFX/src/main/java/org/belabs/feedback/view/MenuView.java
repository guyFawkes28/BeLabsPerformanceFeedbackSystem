package org.belabs.feedback.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.belabs.feedback.controller.MenuController;

public class MenuView {
    private final Scene scene;

    public MenuView(MenuController controller) {
        Button newLabButton = new Button("Preparar nuevo laboratorio");
        newLabButton.setMaxWidth(Double.MAX_VALUE);
        newLabButton.setOnAction(event -> controller.openLaboratories());

        Button newSkillButton = new Button("Agregar nueva habilidad");
        newSkillButton.setMaxWidth(Double.MAX_VALUE);
        newSkillButton.setOnAction(event -> controller.openSkills());

        Button feedbackButton = new Button("Generar feedback de laboratorio");
        feedbackButton.setMaxWidth(Double.MAX_VALUE);
        feedbackButton.setOnAction(event -> controller.openFeedback());

        VBox root = new VBox(12, newLabButton, newSkillButton, feedbackButton);
        root.setPadding(new Insets(20));
        this.scene = new Scene(root, 480, 220);
    }

    public Scene getScene() {
        return scene;
    }
}
