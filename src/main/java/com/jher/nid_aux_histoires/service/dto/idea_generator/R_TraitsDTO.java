package com.jher.nid_aux_histoires.service.dto.idea_generator;

import java.util.Set;

import lombok.Data;

@Data
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
}
