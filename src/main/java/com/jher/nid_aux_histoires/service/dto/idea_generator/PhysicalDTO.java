package com.jher.nid_aux_histoires.service.dto.idea_generator;

public class PhysicalDTO {
	private String eyes_color;
	private String face_shape;
	private String hair_color;
	private String hair_style;
	private String gender;
	private double height;
	private double weight;
	private String morphology;

	public PhysicalDTO() {
	}

	public PhysicalDTO(PhysicalDTO physical) {
		setEyes_color(physical.getEyes_color());
		setFace_shape(physical.getFace_shape());
		setHair_color(physical.getHair_color());
		setHair_style(physical.getHair_style());
		setGender(physical.getGender());
		setHeight(physical.getHeight());
		setWeight(physical.getWeight());
		setMorphology(physical.getMorphology());
	}

	public String getEyes_color() {
		return eyes_color;
	}

	public void setEyes_color(String eyes_color) {
		this.eyes_color = eyes_color;
	}

	public String getFace_shape() {
		return face_shape;
	}

	public void setFace_shape(String face_shape) {
		this.face_shape = face_shape;
	}

	public String getHair_color() {
		return hair_color;
	}

	public void setHair_color(String hair_color) {
		this.hair_color = hair_color;
	}

	public String getHair_style() {
		return hair_style;
	}

	public void setHair_style(String hair_style) {
		this.hair_style = hair_style;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getMorphology() {
		return morphology;
	}

	public void setMorphology(String morphology) {
		this.morphology = morphology;
	}
}
