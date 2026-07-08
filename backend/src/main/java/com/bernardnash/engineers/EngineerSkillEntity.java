package com.bernardnash.engineers;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "engineer_skills")
public class EngineerSkillEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "engineer_id")
	private Long engineerId;

	@Column(name = "skill_name")
	private String skillName;

	@Column(name = "experience_years")
	private Integer experienceYears;

	public EngineerSkillEntity() {
	}

	public EngineerSkillEntity(Long engineerId, String skillName, Integer experienceYears) {
		this.engineerId = engineerId;
		this.skillName = skillName;
		this.experienceYears = experienceYears;
	}

	public Long getId() {
		return id;
	}

	public Long getEngineerId() {
		return engineerId;
	}

	public void setEngineerId(Long engineerId) {
		this.engineerId = engineerId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public Integer getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(Integer experienceYears) {
		this.experienceYears = experienceYears;
	}
}