package com.jher.nid_aux_histoires.service.tool;

import com.jher.nid_aux_histoires.domain.Idea;
import com.jher.nid_aux_histoires.repository.IdeaRepository;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_QuestDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.Random_Interface;

public class QuestGenerator extends RandomEntityGenerator {

	public QuestGenerator(IdeaRepository ideaRepository) {
		super(ideaRepository);
	}

	@Override
	public R_QuestDTO generate(Random_Interface constraint) {
		R_QuestDTO randomQuest = new R_QuestDTO((R_QuestDTO) constraint);
		String giver = getDefaultOrRandom(randomQuest.getGiver(), REG_Element.RACE, REG_Element.ROLE, REG_Element.JOB);
		String receiver = getDefaultOrRandom(randomQuest.getReceiver(), REG_Element.RACE, REG_Element.ROLE,
				REG_Element.JOB);
		String objective = getRandomOrDefaultObjective(randomQuest);
		String hazard = getDefaultOrRandom(randomQuest.getHazard(), REG_Element.HAZARD);
		String consequence = getDefaultOrRandom(randomQuest.getConsequence(), REG_Element.CONSEQUENCE);

		randomQuest.setGiver(giver);
		randomQuest.setReceiver(receiver);
		randomQuest.setObjective(objective);
		randomQuest.setHazard(hazard);
		randomQuest.setConsequence(consequence);

		return randomQuest;
	}

	private String getRandomOrDefaultObjective(R_QuestDTO randomQuest) {
		if (!stringEmpty(randomQuest.getObjective())) {
			return randomQuest.getObjective();
		}

		Idea verb = getRandomIdeaByType(REG_Element.VERB);
		String complement = verb.getComplement();
		String subject = null;

		int x;
		do {
			x = RNG.getRandBelow(complement.length());
		} while (complement.charAt(x) != '1');

		switch (x) {
		case 0:
			subject = getRandomIdeaByType(REG_Element.PLACE).getValue();
			break;
		case 1:
			subject = getDefaultOrRandom(null, REG_Element.RACE, REG_Element.ROLE, REG_Element.JOB);
			break;
		case 2:
			subject = getRandomIdeaByType(REG_Element.OBJECT).getValue();
			break;
		case 3:
			subject = getRandomIdeaByType(REG_Element.EVENT).getValue();
			break;
		}
		return verb.getValue() + " " + subject;
	}
}
