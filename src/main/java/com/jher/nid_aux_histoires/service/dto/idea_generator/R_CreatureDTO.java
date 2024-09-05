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
		this.name = creature.name;
		this.skin = creature.skin;
		this.locations = creature.locations;
		this.diets = creature.diets;
		this.attributes = creature.attributes;
		this.skills = creature.skills;
		this.height = creature.height;
		this.weight = creature.weight;
		this.nbMoveMembers = creature.nbMoveMembers;
	}
}
