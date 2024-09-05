package com.jher.nid_aux_histoires.service.dto.idea_generator;

import java.util.List;

public class TraitsDTO {
	private List<String> bad_traits;
	private List<String> caracteristics;
	private List<String> good_traits;
	private List<String> handicaps;

	public TraitsDTO() {
	}

	public TraitsDTO(TraitsDTO traits) {
		setBad_traits(traits.getBad_traits());
		setGood_traits(traits.getGood_traits());
		setCaracteristics(traits.getCaracteristics());
		setHandicaps(traits.getHandicaps());
	}

	public List<String> getBad_traits() {
		return bad_traits;
	}

	public void setBad_traits(List<String> bad_traits) {
		this.bad_traits = bad_traits;
	}

	public List<String> getCaracteristics() {
		return caracteristics;
	}

	public void setCaracteristics(List<String> caracteristics) {
		this.caracteristics = caracteristics;
	}

	public List<String> getGood_traits() {
		return good_traits;
	}

	public void setGood_traits(List<String> good_traits) {
		this.good_traits = good_traits;
	}

	public List<String> getHandicaps() {
		return handicaps;
	}

	public void setHandicaps(List<String> handicaps) {
		this.handicaps = handicaps;
	}
}
