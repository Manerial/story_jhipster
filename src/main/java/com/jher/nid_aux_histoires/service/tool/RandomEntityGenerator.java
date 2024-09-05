package com.jher.nid_aux_histoires.service.tool;

import java.util.ArrayList;
import java.util.List;

import com.jher.nid_aux_histoires.domain.Idea;
import com.jher.nid_aux_histoires.repository.IdeaRepository;
import com.jher.nid_aux_histoires.service.dto.idea_generator.Random_Interface;

public abstract class RandomEntityGenerator {
	public IdeaRepository ideaRepository;

	public RandomEntityGenerator(IdeaRepository ideaRepository) {
		this.ideaRepository = ideaRepository;
	}

	public Random_Interface generate(Random_Interface constraint) {
		return constraint;
	}

	protected String getDefaultOrRandom(String defaultValue, REG_Element type) {
		return (!stringEmpty(defaultValue)) ? defaultValue : getRandomIdeaByType(type).getValue();
	}

	protected List<String> getDefaultOrRandom(List<String> defaultValues, String type, int defaultMin, int defaultMax) {
		if (defaultValues != null && defaultValues.size() > 0) {
			return defaultValues;
		}
		List<String> ideas = new ArrayList<String>();
		List<Idea> ideaResources = ideaRepository.findByType(type);
		int number = (int) RNG.getRandomIntoInterval(defaultMin, defaultMax);
		if (number == 0) {
			ideas.add("Aucun");
		}
		while (ideas.size() < number) {
			int index = RNG.getRandBelow(ideaResources.size());
			String hasSash = (ideas.size() < number - 1) ? "," : "";
			if (!ideas.contains(ideaResources.get(index).getValue())) {
				ideas.add(ideaResources.get(index).getValue() + hasSash);
			}
		}
		return ideas;
	}

	protected String getDefaultOrRandomWithArticle(String defaultValue, REG_Element element, REG_Article articleType) {
		return (!stringEmpty(defaultValue)) ? defaultValue : getRandomIdeaByTypeWithArticle(element, articleType);
	}

	protected int getDefaultOrRandom(int defaultValue, int defaultMax) {
		return (defaultValue > 0) ? defaultValue : RNG.getRandBelow(defaultMax);
	}

	protected Idea getRandomIdeaByType(REG_Element type) {
		List<Idea> resources = ideaRepository.findByType(type.toString());
		int index = RNG.getRandBelow(resources.size());
		return resources.get(index);
	}

	protected String getRandomIdeaByTypeWithArticle(REG_Element type, REG_Article articleType) {
		Idea idea = getRandomIdeaByType(type);
		String complement = idea.getComplement();

		if (complement == null) {
			return idea.getValue().toLowerCase();
		}

		if (!complement.equals("P") && articleType != REG_Article.article_indefini && startWithVowel(idea.getValue())) {
			complement = "A";
		}

		String article = ideaRepository.findByTypeAndComplement(articleType.toString(), complement).getValue();

		return article + idea.getValue().toLowerCase();
	}

	protected boolean stringEmpty(String value) {
		return value == null || value.equals("");
	}

	private boolean startWithVowel(String str) {
		return str.toLowerCase().matches("^(a|e|é|i|o|u|y).*$");
	}
}