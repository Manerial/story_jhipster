package com.jher.nid_aux_histoires.service.tool;

// @formatter:off
public enum REG_Element {
	// Persona
	EYES_COLOR,
	FACE_SHAPE,
	HAIR_COLOR,
	HAIR_STYLE,
	MORPHOLOGY,
	ROLE,
	GENDER,
	BAD_TRAIT,
	HOBBY,
	GOOD_TRAIT,
	HANDICAP,
	JOB,
	RACE,
	
	// Creature
	MOVE,
	SKIN,
	DIET,
	ATTRIBUTE,
	SKILL,
	
	// Writing Option
	STYLE,
	THEME,
	
	// Location
	PLACE,
	LANDSCAPE,
	
	// Object
	OBJECT,
	MATERIAL,
	
	// Global
	COMMON_NAME,
	ADJECTIVE;
	
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
// @formatter:on
