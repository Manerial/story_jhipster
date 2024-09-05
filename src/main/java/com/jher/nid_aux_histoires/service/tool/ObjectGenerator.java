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
		String object = getDefaultOrRandomWithArticle(randomObject.getObject(), REG_Element.object,
				REG_Article.article_defini);
		String adjective = getDefaultOrRandom(randomObject.getAdjective(), REG_Element.adjective);
		String suffix = getDefaultOrRandomWithArticle(randomObject.getSuffix(), REG_Element.material,
				REG_Article.article_partitif_second);

		randomObject.setAdjective(adjective);
		randomObject.setObject(object);
		randomObject.setSuffix(suffix);
		return randomObject;
	}
}
