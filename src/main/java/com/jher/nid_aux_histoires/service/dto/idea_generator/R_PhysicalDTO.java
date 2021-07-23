package com.jher.nid_aux_histoires.service.dto.idea_generator;

public class R_PhysicalDTO implements Random_Interface {
	private String eyesColor;
	private String faceShape;
	private String hairColor;
	private String hairStyle;
	private String gender;
	private double height;
	private double weight;
	private String morphology;

	public R_PhysicalDTO() {
	}

	public R_PhysicalDTO(R_PhysicalDTO physical) {
		setEyesColor(physical.getEyesColor());
		setFaceShape(physical.getFaceShape());
		setHairColor(physical.getHairColor());
		setHairStyle(physical.getHairStyle());
		setGender(physical.getGender());
		setHeight(physical.getHeight());
		setWeight(physical.getWeight());
		setMorphology(physical.getMorphology());
	}

	public String getEyesColor() {
		return eyesColor;
	}

	public void setEyesColor(String eyes_color) {
		this.eyesColor = eyes_color;
	}

	public String getFaceShape() {
		return faceShape;
	}

	public void setFaceShape(String faceShape) {
		this.faceShape = faceShape;
	}

	public String getHairColor() {
		return hairColor;
	}

	public void setHairColor(String hairColor) {
		this.hairColor = hairColor;
	}

	public String getHairStyle() {
		return hairStyle;
	}

	public void setHairStyle(String hair_style) {
		this.hairStyle = hair_style;
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
