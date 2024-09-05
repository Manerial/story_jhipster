package com.jher.nid_aux_histoires.service.tool;

import com.jher.nid_aux_histoires.repository.IdeaRepository;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_LocationDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.Random_Interface;

public class LocationGenerator extends RandomEntityGenerator {
	public LocationGenerator(IdeaRepository ideaRepository) {
		super(ideaRepository);
	}

	@Override
	public R_LocationDTO generate(Random_Interface constraint) {
		R_LocationDTO randomLocation = new R_LocationDTO((R_LocationDTO) constraint);
		String place = getDefaultOrRandomWithArticle(randomLocation.getPlace(), REG_Element.PLACE,
				REG_Article.ARTICLE_INDEFINI);
		String landscape = getDefaultOrRandomWithArticle(randomLocation.getLandscape(), REG_Element.LANDSCAPE,
				REG_Article.ARTICLE_INDEFINI);
		String material = getDefaultOrRandomWithArticle(randomLocation.getMaterial(), REG_Element.MATERIAL,
				REG_Article.ARTICLE_PARTITIF_SECOND);
		randomLocation.setPlace(place);
		randomLocation.setLandscape(landscape);
		randomLocation.setMaterial(material);
		return randomLocation;
	}
}
