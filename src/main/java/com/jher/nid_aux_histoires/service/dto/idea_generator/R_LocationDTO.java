package com.jher.nid_aux_histoires.service.dto.idea_generator;

import lombok.Data;

@Data
public class R_LocationDTO implements Random_Interface {
	private String place;
	private String landscape;
	private String material;

	public R_LocationDTO() {
	}

	public R_LocationDTO(R_LocationDTO location) {
		setPlace(location.getPlace());
		setLandscape(location.getLandscape());
		setMaterial(location.getMaterial());
	}
}
