package org.belabs.feedback.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.belabs.feedback.model.Laboratory;
import org.belabs.feedback.model.Skill;
import org.belabs.feedback.service.LaboratoryService;
import org.belabs.feedback.service.SkillService;
import org.belabs.feedback.util.AlertUtil;
import org.belabs.feedback.view.LaboratoryView;

import java.util.List;

public class LaboratoryController {
    private final LaboratoryService laboratoryService;
    private final SkillService skillService;
    private LaboratoryView view;

    public LaboratoryController(LaboratoryService laboratoryService, SkillService skillService) {
        this.laboratoryService = laboratoryService;
        this.skillService = skillService;
    }

    public void setView(LaboratoryView view) {
        this.view = view;
    }

    public ObservableList<Skill> loadSkills() {
        return FXCollections.observableArrayList(skillService.getAllSkills());
    }

    public ObservableList<Laboratory> loadLaboratories() {
        ObservableList<Laboratory> laboratories = FXCollections.observableArrayList(laboratoryService.getAllLaboratories());
        if (view != null) {
            view.setLaboratories(laboratories);
        }
        return laboratories;
    }

    public void handleCreateLaboratory(String nombre, List<Skill> selectedSkills) {
        try {
            laboratoryService.createLaboratory(nombre, selectedSkills.stream().map(Skill::getId).toList());
            loadLaboratories();
            view.clearForm();
            AlertUtil.showInfo("Laboratorios", "Laboratorio creado correctamente.");
        } catch (Exception exception) {
            AlertUtil.showError("Laboratorios", exception.getMessage());
        }
    }
}
