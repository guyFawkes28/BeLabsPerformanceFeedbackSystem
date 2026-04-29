package org.belabs.feedback.model;

public class LaboratorySkill {
    private int id;
    private int laboratoryId;
    private int skillId;

    public LaboratorySkill() {
    }

    public LaboratorySkill(int id, int laboratoryId, int skillId) {
        this.id = id;
        this.laboratoryId = laboratoryId;
        this.skillId = skillId;
    }

    public int getId() {
        return id;
    }

    public int getLaboratoryId() {
        return laboratoryId;
    }

    public int getSkillId() {
        return skillId;
    }
}
