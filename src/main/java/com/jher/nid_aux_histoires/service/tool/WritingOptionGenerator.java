package com.jher.nid_aux_histoires.service.tool;

import com.jher.nid_aux_histoires.repository.IdeaRepository;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_WritingOptionDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.Random_Interface;

public class WritingOptionGenerator extends RandomEntityGenerator {
	public WritingOptionGenerator(IdeaRepository ideaRepository) {
		super(ideaRepository);
	}

	@Override
	public R_WritingOptionDTO generate(Random_Interface constraint) {
		R_WritingOptionDTO randomWritingOption = new R_WritingOptionDTO((R_WritingOptionDTO) constraint);
		String style = getDefaultOrRandom(randomWritingOption.getStyle(), REG_Element.style);
		String theme = getDefaultOrRandom(randomWritingOption.getTheme(), REG_Element.theme);
		randomWritingOption.setStyle(style);
		randomWritingOption.setTheme(theme);
		return randomWritingOption;
	}
}
