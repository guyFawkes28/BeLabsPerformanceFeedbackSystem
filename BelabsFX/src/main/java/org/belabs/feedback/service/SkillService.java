package org.belabs.feedback.service;

import org.belabs.feedback.model.Skill;
import org.belabs.feedback.repository.SkillRepository;

import java.util.List;

public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public void createSkill(Skill skill) {
        validate(skill);
        skillRepository.save(skill);
    }

    public void updateSkill(Skill skill) {
        validate(skill);
        skillRepository.update(skill);
    }

    public void deleteSkill(int id) {
        skillRepository.delete(id);
    }

    private void validate(Skill skill) {
        if (skill.getNombre() == null || skill.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la habilidad es obligatorio.");
        }
        if (skill.getAlto() == null || skill.getAlto().isBlank()
                || skill.getMedio() == null || skill.getMedio().isBlank()
                || skill.getBajo() == null || skill.getBajo().isBlank()) {
            throw new IllegalArgumentException("Todos los textos de feedback son obligatorios.");
        }
    }
}
