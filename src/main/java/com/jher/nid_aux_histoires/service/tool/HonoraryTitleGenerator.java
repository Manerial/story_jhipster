package com.jher.nid_aux_histoires.service.tool;

import com.jher.nid_aux_histoires.repository.IdeaRepository;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_HonoraryTitleDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.Random_Interface;

public class HonoraryTitleGenerator extends RandomEntityGenerator {

	public HonoraryTitleGenerator(IdeaRepository ideaRepository) {
		super(ideaRepository);
	}

	@Override
	public R_HonoraryTitleDTO generate(Random_Interface constraint) {
		String title;
		title = getPart1();
		title += " " + getPart3().toLowerCase();

		R_HonoraryTitleDTO titleDTO = new R_HonoraryTitleDTO(title);
		return titleDTO;
	}

	private String getPart1() {
		switch (RNG.getRandBelow(3)) {
		case 0:
			return generateElement(REG_Element.JOB, REG_Article.ARTICLE_DEFINI) + " "
					+ getPart2(REG_Element.RACE).toLowerCase();
		case 1:
			return generateElement(REG_Element.RACE, REG_Article.ARTICLE_DEFINI) + " "
					+ getPart2(REG_Element.JOB).toLowerCase();
		case 2:
			return generateElement(REG_Element.ADJECTIVE, REG_Article.ARTICLE_DEFINI);
		default:
			break;
		}
		return "";
	}

	private String getPart2(REG_Element element1) {
		switch (RNG.getRandBelow(3)) {
		case 0:
			return generateElement(element1, null);
		case 1:
			return generateElement(REG_Element.ADJECTIVE, null);
		case 2:
			return "";
		default:
			break;
		}
		return "";
	}

	private String getPart3() {
		switch (RNG.getRandBelow(3)) {
		case 0:
			return generateElement(REG_Element.OBJECT, REG_Article.ARTICLE_CONTRACTIF) + " "
					+ ((RNG.getRandBelow(2) == 0)
							? generateElement(REG_Element.MATERIAL, REG_Article.ARTICLE_PARTITIF_SECOND)
							: generateElement(REG_Element.ADJECTIVE, null));
		case 1:
			return generateElement(REG_Element.ADJECTIVE, null);
		case 2:
			return "";
		default:
			break;
		}
		return "";
	}

	private String generateElement(REG_Element element, REG_Article article) {
		if (article != null) {
			return getRandomIdeaByTypeWithArticle(element, article);
		}

		return getRandomIdeaByType(element).getValue();
	}
}
