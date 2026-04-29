package org.belabs.feedback.service;

import org.belabs.feedback.model.Laboratory;
import org.belabs.feedback.model.Skill;
import org.belabs.feedback.repository.LaboratoryRepository;
import org.belabs.feedback.repository.SkillRepository;

import java.util.ArrayList;
import java.util.List;

public class LaboratoryService {
    private final LaboratoryRepository laboratoryRepository;
    private final SkillRepository skillRepository;

    public LaboratoryService(LaboratoryRepository laboratoryRepository, SkillRepository skillRepository) {
        this.laboratoryRepository = laboratoryRepository;
        this.skillRepository = skillRepository;
    }

    public void createLaboratory(String nombre, List<Integer> skillIds) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del laboratorio es obligatorio.");
        }
        if (skillIds == null || skillIds.size() != 4) {
            throw new IllegalArgumentException("Debe seleccionar exactamente 4 habilidades.");
        }

        List<Skill> skills = new ArrayList<>();
        for (Integer skillId : skillIds) {
            Skill skill = skillRepository.findById(skillId);
            if (skill != null) {
                skills.add(skill);
            }
        }
        if (skills.size() != 4) {
            throw new IllegalArgumentException("No fue posible cargar las 4 habilidades seleccionadas.");
        }

        laboratoryRepository.save(new Laboratory(0, nombre, skills));
    }

    public List<Laboratory> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }
}
