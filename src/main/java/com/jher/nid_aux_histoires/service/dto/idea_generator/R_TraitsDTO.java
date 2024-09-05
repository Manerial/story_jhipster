package com.jher.nid_aux_histoires.service.dto.idea_generator;

import java.util.Set;

public class R_TraitsDTO implements Random_Interface {
	private Set<String> badTraits;
	private Set<String> caracteristics;
	private Set<String> goodTraits;
	private Set<String> handicaps;

	public R_TraitsDTO() {
	}

	public R_TraitsDTO(R_TraitsDTO traits) {
		setBadTraits(traits.getBadTraits());
		setGoodTraits(traits.getGoodTraits());
		setCaracteristics(traits.getCaracteristics());
		setHandicaps(traits.getHandicaps());
	}

	public Set<String> getBadTraits() {
		return badTraits;
	}

	public void setBadTraits(Set<String> badTraits) {
		this.badTraits = badTraits;
	}

	public Set<String> getCaracteristics() {
		return caracteristics;
	}

	public void setCaracteristics(Set<String> caracteristics) {
		this.caracteristics = caracteristics;
	}

	public Set<String> getGoodTraits() {
		return goodTraits;
	}

	public void setGoodTraits(Set<String> goodTraits) {
		this.goodTraits = goodTraits;
	}

	public Set<String> getHandicaps() {
		return handicaps;
	}

	public void setHandicaps(Set<String> handicaps) {
		this.handicaps = handicaps;
	}
}
