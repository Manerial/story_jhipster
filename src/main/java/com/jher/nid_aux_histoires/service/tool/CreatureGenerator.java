package com.jher.nid_aux_histoires.service.tool;

import java.util.Set;

import com.jher.nid_aux_histoires.repository.IdeaRepository;
import com.jher.nid_aux_histoires.service.WordAnalysisService;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_CreatureDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.Random_Interface;

public class CreatureGenerator extends RandomEntityGenerator {
	private WordAnalysisService wordAnalysisService;

	public CreatureGenerator(IdeaRepository ideaRepository, WordAnalysisService wordAnalysisService) {
		super(ideaRepository);
		this.wordAnalysisService = wordAnalysisService;
	}

	@Override
	public R_CreatureDTO generate(Random_Interface constraint) {
		R_CreatureDTO randomCreature = new R_CreatureDTO((R_CreatureDTO) constraint);

		String name = getRandomName(randomCreature.getName());
		String skin = getDefaultOrRandom(randomCreature.getSkin(), REG_Element.skin);
		Set<String> locations = getDefaultOrRandom(randomCreature.getLocations(), REG_Element.landscape, 1, 5);
		Set<String> diets = getDefaultOrRandom(randomCreature.getDiets(), REG_Element.diet, 1, 3);
		Set<String> attributes = getDefaultOrRandom(randomCreature.getAttributes(), REG_Element.attribute, 2, 7);
		Set<String> skills = getDefaultOrRandom(randomCreature.getSkills(), REG_Element.skill, 1, 4);
		double height = getHeight(randomCreature.getHeight());
		double weight = getWeight(randomCreature.getWeight(), height);
		int nbMoveMembers = randomCreature.getNbMoveMembers();
		if (nbMoveMembers < 0) {
			nbMoveMembers = (int) RNG.useRandomVariable(1, 5, 1.2) * 2;
		}

		randomCreature.setName(name);
		randomCreature.setSkin(skin);
		randomCreature.setLocations(locations);
		randomCreature.setDiets(diets);
		randomCreature.setAttributes(attributes);
		randomCreature.setSkills(skills);
		randomCreature.setHeight(height);
		randomCreature.setWeight(weight);
		randomCreature.setNbMoveMembers(nbMoveMembers);

		return randomCreature;
	}

	private String getRandomName(String defaultName) {
		if (!stringEmpty(defaultName)) {
			return defaultName;
		}
		return wordAnalysisService.generate("animal");
	}

	private double getHeight(double defaultHeight) {
		if (defaultHeight > 0) {
			return defaultHeight;
		}
		double height = 0;
		switch (RNG.getRandBelow(3)) {
		case 0:
			height = RNG.getRandomIntoInterval(0.2, 10);
			break;
		case 1:
			height = RNG.getRandomIntoInterval(10, 1000);
			break;
		case 2:
			height = RNG.getRandomIntoInterval(1000, 5000);
			break;
		}
		return height;
	}

	private double getWeight(double defaultWeight, double height) {
		if (defaultWeight > 0) {
			return defaultWeight;
		}
		double minWeight = height * height * 1.5;
		double maxWeight = height * height * 3.1;
		return RNG.getRandomIntoInterval(minWeight, maxWeight);
	}
}
