package com.jher.nid_aux_histoires.service.tool;

// @formatter:off
public enum REG_WordAnalysis {
	NAME_ANGELS,
	NAME_ANIMALS,
	NAME_BOYS,
	NAME_DEMONS,
	NAME_FIRSTNAMES,
	NAME_GIRLS,
	NAME_GREEK,
	NAME_NORDIC,
	WORD_FRENCH;
	
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
// @formatter:on
