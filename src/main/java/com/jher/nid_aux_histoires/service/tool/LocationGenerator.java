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
		String place = getDefaultOrRandomWithArticle(randomLocation.getPlace(), REG_Element.place,
				REG_Article.article_indefini);
		String landscape = getDefaultOrRandomWithArticle(randomLocation.getLandscape(), REG_Element.landscape,
				REG_Article.article_indefini);
		String material = getDefaultOrRandomWithArticle(randomLocation.getMaterial(), REG_Element.material,
				REG_Article.article_partitif_second);
		randomLocation.setPlace(place);
		randomLocation.setLandscape(landscape);
		randomLocation.setMaterial(material);
		return randomLocation;
	}
}
