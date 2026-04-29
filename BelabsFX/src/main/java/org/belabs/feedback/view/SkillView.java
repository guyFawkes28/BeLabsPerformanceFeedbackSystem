package org.belabs.feedback.view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.belabs.feedback.controller.SkillController;
import org.belabs.feedback.model.Skill;

public class SkillView {
    private final Scene scene;
    private final SkillController controller;
    private final TableView<Skill> tableView = new TableView<>();
    private final TextField nombreField = new TextField();
    private final TextArea altoArea = new TextArea();
    private final TextArea medioArea = new TextArea();
    private final TextArea bajoArea = new TextArea();
    private Skill selectedSkill;

    public SkillView(SkillController controller, Runnable onBack) {
        this.controller = controller;

        TableColumn<Skill, Number> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
        TableColumn<Skill, String> nombreColumn = new TableColumn<>("Nombre");
        nombreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tableView.getColumns().addAll(idColumn, nombreColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> loadSelection(newValue));

        nombreField.setPromptText("Nombre");
        altoArea.setPromptText("Feedback alto");
        medioArea.setPromptText("Feedback medio");
        bajoArea.setPromptText("Feedback bajo");
        altoArea.setPrefRowCount(3);
        medioArea.setPrefRowCount(3);
        bajoArea.setPrefRowCount(3);

        Button createButton = new Button("Crear");
        createButton.setOnAction(event -> controller.handleCreateSkill(buildSkill(false)));
        Button updateButton = new Button("Actualizar");
        updateButton.setOnAction(event -> controller.handleUpdateSkill(buildSkill(true)));
        Button deleteButton = new Button("Eliminar");
        deleteButton.setOnAction(event -> controller.handleDeleteSkill(selectedSkill));
        Button refreshButton = new Button("Recargar");
        refreshButton.setOnAction(event -> refreshData());
        Button backButton = new Button("Volver al menu");
        backButton.setOnAction(event -> onBack.run());

        HBox actions = new HBox(10, createButton, updateButton, deleteButton, refreshButton, backButton);
        GridPane form = new GridPane();
        form.setVgap(8);
        form.setHgap(8);
        form.addRow(0, new Label("Nombre"), nombreField);
        form.addRow(1, new Label("Alto"), altoArea);
        form.addRow(2, new Label("Medio"), medioArea);
        form.addRow(3, new Label("Bajo"), bajoArea);

        VBox content = new VBox(12, form, actions);
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));
        root.setCenter(tableView);
        root.setBottom(content);
        this.scene = new Scene(root, 900, 600);
    }

    public void refreshData() {
        controller.loadSkills();
    }

    public void setSkills(ObservableList<Skill> skills) {
        tableView.setItems(skills);
    }

    public void clearForm() {
        selectedSkill = null;
        nombreField.clear();
        altoArea.clear();
        medioArea.clear();
        bajoArea.clear();
        tableView.getSelectionModel().clearSelection();
    }

    public Scene getScene() {
        return scene;
    }

    private Skill buildSkill(boolean includeId) {
        Skill skill = new Skill(nombreField.getText(), altoArea.getText(), medioArea.getText(), bajoArea.getText());
        if (includeId) {
            if (selectedSkill == null) {
                throw new IllegalArgumentException("Seleccione una habilidad para actualizar.");
            }
            skill.setId(selectedSkill.getId());
        }
        return skill;
    }

    private void loadSelection(Skill skill) {
        selectedSkill = skill;
        if (skill == null) {
            return;
        }
        nombreField.setText(skill.getNombre());
        altoArea.setText(skill.getAlto());
        medioArea.setText(skill.getMedio());
        bajoArea.setText(skill.getBajo());
    }
}
