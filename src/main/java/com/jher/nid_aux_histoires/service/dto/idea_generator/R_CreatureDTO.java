package com.jher.nid_aux_histoires.service.dto.idea_generator;

import java.util.Set;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public Set<String> getLocations() {
		return locations;
	}

	public void setLocations(Set<String> locations) {
		this.locations = locations;
	}

	public Set<String> getDiets() {
		return diets;
	}

	public void setDiets(Set<String> diets) {
		this.diets = diets;
	}

	public Set<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<String> attributes) {
		this.attributes = attributes;
	}

	public Set<String> getSkills() {
		return skills;
	}

	public void setSkills(Set<String> skills) {
		this.skills = skills;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getNbMoveMembers() {
		return nbMoveMembers;
	}

	public void setNbMoveMembers(int nbMoveMembers) {
		this.nbMoveMembers = nbMoveMembers;
	}
}
