package com.bernardnash.engineers;

import org.springframework.stereotype.Service;

@Service
public class SkillsService {
	private final SkillsRepository skillsRepository;

	public SkillsService( final SkillsRepository skillsRepository ) {this.skillsRepository = skillsRepository;}

	public void addSkillToEngineer(Long engineerId, String skill) {

		EngineerSkillEntity skillEntity = new EngineerSkillEntity(engineerId, skill, 1);

		skillsRepository.save( skillEntity );
	}

}

