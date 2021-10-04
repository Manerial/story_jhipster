package com.jher.nid_aux_histoires.service.dto.idea_generator;

import lombok.Data;

@Data
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
}
