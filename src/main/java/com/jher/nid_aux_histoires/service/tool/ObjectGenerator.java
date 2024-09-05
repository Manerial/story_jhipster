package com.jher.nid_aux_histoires.service.tool;

import com.jher.nid_aux_histoires.repository.IdeaRepository;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_ObjectDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.Random_Interface;

public class ObjectGenerator extends RandomEntityGenerator {

	public ObjectGenerator(IdeaRepository ideaRepository) {
		super(ideaRepository);
	}

	@Override
	public R_ObjectDTO generate(Random_Interface constraint) {
		R_ObjectDTO randomObject = new R_ObjectDTO((R_ObjectDTO) constraint);
		String object = getDefaultOrRandomWithArticle(randomObject.getObject(), REG_Element.OBJECT,
				REG_Article.ARTICLE_DEFINI);
		String adjective = getDefaultOrRandom(randomObject.getAdjective(), REG_Element.ADJECTIVE);
		String suffix = getDefaultOrRandomWithArticle(randomObject.getSuffix(), REG_Element.MATERIAL,
				REG_Article.ARTICLE_PARTITIF_SECOND);

		randomObject.setAdjective(adjective);
		randomObject.setObject(object);
		randomObject.setSuffix(suffix);
		return randomObject;
	}
}
