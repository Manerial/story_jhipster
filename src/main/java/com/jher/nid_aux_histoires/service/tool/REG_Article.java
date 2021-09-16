package com.jher.nid_aux_histoires.service.tool;

public enum REG_Article {
	ARTICLE_DEFINI, // le, la, les, l'
	ARTICLE_INDEFINI, // un, une, des, X
	ARTICLE_PARTITIF, // du, de la, des, d'
	ARTICLE_PARTITIF_SECOND, // de, de, des, d'
	ARTICLE_CONTRACTIF; // au, à la, aux, à l'

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
