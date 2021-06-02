package com.jher.nid_aux_histoires.service.dto.idea_generator;

import java.util.List;

public class R_TraitsDTO {
	private List<String> badTraits;
	private List<String> caracteristics;
	private List<String> goodTraits;
	private List<String> handicaps;

	public R_TraitsDTO() {
	}

	public R_TraitsDTO(R_TraitsDTO traits) {
		setBadTraits(traits.getBadTraits());
		setGoodTraits(traits.getGoodTraits());
		setCaracteristics(traits.getCaracteristics());
		setHandicaps(traits.getHandicaps());
	}

	public List<String> getBadTraits() {
		return badTraits;
	}

	public void setBadTraits(List<String> badTraits) {
		this.badTraits = badTraits;
	}

	public List<String> getCaracteristics() {
		return caracteristics;
	}

	public void setCaracteristics(List<String> caracteristics) {
		this.caracteristics = caracteristics;
	}

	public List<String> getGoodTraits() {
		return goodTraits;
	}

	public void setGoodTraits(List<String> goodTraits) {
		this.goodTraits = goodTraits;
	}

	public List<String> getHandicaps() {
		return handicaps;
	}

	public void setHandicaps(List<String> handicaps) {
		this.handicaps = handicaps;
	}
}
