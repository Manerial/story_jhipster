package com.jher.nid_aux_histoires.service.dto.idea_generator;

import java.util.Set;

import lombok.Data;

@Data
public class R_CreatureDTO implements Random_Interface {
	private String name;
	private String skin;
	private Set<String> locations;
	private Set<String> diets;
	private Set<String> attributes;
	private Set<String> skills;
	private double height;
	private double weight;
	private int nbMoveMembers;

	public R_CreatureDTO() {
	}

	public R_CreatureDTO(R_CreatureDTO creature) {
		setName(creature.getName());
		setSkin(creature.getSkin());
		setLocations(creature.getLocations());
		setDiets(creature.getDiets());
		setAttributes(creature.getAttributes());
		setSkills(creature.getSkills());
		setHeight(creature.getHeight());
		setWeight(creature.getWeight());
		setNbMoveMembers(creature.getNbMoveMembers());
	}
}
