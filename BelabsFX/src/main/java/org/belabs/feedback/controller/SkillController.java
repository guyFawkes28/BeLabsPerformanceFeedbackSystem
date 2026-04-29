package org.belabs.feedback.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.belabs.feedback.model.Skill;
import org.belabs.feedback.service.SkillService;
import org.belabs.feedback.util.AlertUtil;
import org.belabs.feedback.view.SkillView;

public class SkillController {
    private final SkillService skillService;
    private SkillView view;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    public void setView(SkillView view) {
        this.view = view;
    }

    public void handleCreateSkill(Skill skill) {
        try {
            skillService.createSkill(skill);
            loadSkills();
            view.clearForm();
            AlertUtil.showInfo("Habilidades", "Habilidad creada correctamente.");
        } catch (Exception exception) {
            AlertUtil.showError("Habilidades", exception.getMessage());
        }
    }

    public void handleUpdateSkill(Skill skill) {
        try {
            skillService.updateSkill(skill);
            loadSkills();
            view.clearForm();
            AlertUtil.showInfo("Habilidades", "Habilidad actualizada correctamente.");
        } catch (Exception exception) {
            AlertUtil.showError("Habilidades", exception.getMessage());
        }
    }

    public void handleDeleteSkill(Skill skill) {
        if (skill == null) {
            AlertUtil.showError("Habilidades", "Seleccione una habilidad para eliminar.");
            return;
        }
        try {
            skillService.deleteSkill(skill.getId());
            loadSkills();
            view.clearForm();
            AlertUtil.showInfo("Habilidades", "Habilidad eliminada correctamente.");
        } catch (Exception exception) {
            AlertUtil.showError("Habilidades", exception.getMessage());
        }
    }

    public ObservableList<Skill> loadSkills() {
        ObservableList<Skill> skills = FXCollections.observableArrayList(skillService.getAllSkills());
        if (view != null) {
            view.setSkills(skills);
        }
        return skills;
    }
}
