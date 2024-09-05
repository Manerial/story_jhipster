package com.jher.nid_aux_histoires.service.tool;

//@formatter:off
public enum REG_Entity {
	PERSONA,
	OBJECT,
	WRITING_OPTION,
	LOCATION,
	WORD,
	HONORARY_TITLE,
	CREATURE;
	
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
//@formatter:on