package org.belabs.feedback.view;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.belabs.feedback.util.AlertUtil;
import org.belabs.feedback.controller.LaboratoryController;
import org.belabs.feedback.model.Laboratory;
import org.belabs.feedback.model.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LaboratoryView {
    private final Scene scene;
    private final LaboratoryController controller;
    private final TextField nombreField = new TextField();
    private final ListView<Skill> availableSkillsListView = new ListView<>();
    private final ListView<Skill> selectedSkillsListView = new ListView<>();
    private final ListView<Laboratory> laboratoriesListView = new ListView<>();
    private final Label selectedCountLabel = new Label("Seleccionadas: 0 de 4");

    public LaboratoryView(LaboratoryController controller, Runnable onBack) {
        this.controller = controller;
        nombreField.setPromptText("Nombre del laboratorio");

        availableSkillsListView.setPlaceholder(new Label("No hay habilidades disponibles"));
        selectedSkillsListView.setPlaceholder(new Label("Aun no has agregado habilidades"));
        availableSkillsListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Skill item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });
        selectedSkillsListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Skill item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });
        laboratoriesListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Laboratory item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre() + " - " + item.getSkills().stream().map(Skill::getNombre).collect(Collectors.joining(", ")));
            }
        });

        Button addButton = new Button("Agregar ->");
        addButton.setMaxWidth(Double.MAX_VALUE);
        addButton.setOnAction(event -> addSelectedSkill());
        Button removeButton = new Button("<- Quitar");
        removeButton.setMaxWidth(Double.MAX_VALUE);
        removeButton.setOnAction(event -> removeSelectedSkill());
        Button moveUpButton = new Button("Subir");
        moveUpButton.setMaxWidth(Double.MAX_VALUE);
        moveUpButton.setOnAction(event -> moveSelectedSkill(-1));
        Button moveDownButton = new Button("Bajar");
        moveDownButton.setMaxWidth(Double.MAX_VALUE);
        moveDownButton.setOnAction(event -> moveSelectedSkill(1));
        Button createButton = new Button("Crear laboratorio");
        createButton.setOnAction(event -> controller.handleCreateLaboratory(nombreField.getText(), getSelectedSkills()));
        Button backButton = new Button("Volver al menu");
        backButton.setOnAction(event -> onBack.run());

        VBox transferButtons = new VBox(10, addButton, removeButton, moveUpButton, moveDownButton);
        transferButtons.setAlignment(Pos.CENTER);

        VBox availableBox = new VBox(8, new Label("Habilidades disponibles"), availableSkillsListView);
        VBox selectedBox = new VBox(8, new Label("Pila de habilidades del laboratorio"), selectedCountLabel, selectedSkillsListView);
        HBox.setHgrow(availableBox, Priority.ALWAYS);
        HBox.setHgrow(selectedBox, Priority.ALWAYS);
        VBox.setVgrow(availableSkillsListView, Priority.ALWAYS);
        VBox.setVgrow(selectedSkillsListView, Priority.ALWAYS);

        HBox selectorBox = new HBox(12, availableBox, transferButtons, selectedBox);
        HBox actions = new HBox(10, createButton, backButton);
        VBox form = new VBox(10, new Label("Nombre"), nombreField, new Label("Agrega exactamente 4 habilidades y ajusta su orden si lo necesitas"), selectorBox, actions);
        form.setPadding(new Insets(10));
        VBox.setVgrow(selectorBox, Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));
        root.setLeft(form);
        root.setCenter(new VBox(10, new Label("Laboratorios creados"), laboratoriesListView));
        this.scene = new Scene(root, 1080, 600);
    }

    public void refreshData() {
        ObservableList<Skill> skills = controller.loadSkills();
        availableSkillsListView.setItems(skills);
        selectedSkillsListView.getItems().clear();
        updateSelectedCount();
        controller.loadLaboratories();
    }

    public void setLaboratories(ObservableList<Laboratory> laboratories) {
        laboratoriesListView.setItems(laboratories);
    }

    public void clearForm() {
        nombreField.clear();
        availableSkillsListView.getSelectionModel().clearSelection();
        selectedSkillsListView.getItems().clear();
        updateSelectedCount();
    }

    public List<Skill> getSelectedSkills() {
        return List.copyOf(selectedSkillsListView.getItems());
    }

    public Scene getScene() {
        return scene;
    }

    private void addSelectedSkill() {
        Skill skill = availableSkillsListView.getSelectionModel().getSelectedItem();
        if (skill == null) {
            AlertUtil.showError("Laboratorios", "Selecciona una habilidad disponible para agregar.");
            return;
        }
        if (selectedSkillsListView.getItems().contains(skill)) {
            AlertUtil.showError("Laboratorios", "Esa habilidad ya fue agregada al laboratorio.");
            return;
        }
        if (selectedSkillsListView.getItems().size() >= 4) {
            AlertUtil.showError("Laboratorios", "Solo puedes agregar 4 habilidades.");
            return;
        }
        selectedSkillsListView.getItems().add(skill);
        selectedSkillsListView.getSelectionModel().select(skill);
        updateSelectedCount();
    }

    private void removeSelectedSkill() {
        Skill skill = selectedSkillsListView.getSelectionModel().getSelectedItem();
        if (skill == null) {
            AlertUtil.showError("Laboratorios", "Selecciona una habilidad de la pila para quitar.");
            return;
        }
        selectedSkillsListView.getItems().remove(skill);
        updateSelectedCount();
    }

    private void moveSelectedSkill(int direction) {
        int selectedIndex = selectedSkillsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            AlertUtil.showError("Laboratorios", "Selecciona una habilidad de la pila para mover.");
            return;
        }
        int targetIndex = selectedIndex + direction;
        if (targetIndex < 0 || targetIndex >= selectedSkillsListView.getItems().size()) {
            return;
        }
        List<Skill> reordered = new ArrayList<>(selectedSkillsListView.getItems());
        Skill skill = reordered.remove(selectedIndex);
        reordered.add(targetIndex, skill);
        selectedSkillsListView.getItems().setAll(reordered);
        selectedSkillsListView.getSelectionModel().select(targetIndex);
    }

    private void updateSelectedCount() {
        selectedCountLabel.setText("Seleccionadas: " + selectedSkillsListView.getItems().size() + " de 4");
    }
}
