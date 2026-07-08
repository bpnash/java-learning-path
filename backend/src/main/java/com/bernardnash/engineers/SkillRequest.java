package com.bernardnash.engineers;

public class SkillRequest {


	private Long engineerId;
	private String skillName;
	private Integer experienceYears;

	public SkillRequest(Long engineerId, String skillName, Integer experienceYears) {
		this.engineerId = engineerId;
		this.skillName = skillName;
		this.experienceYears = experienceYears;
	}

	public SkillRequest() {}

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
