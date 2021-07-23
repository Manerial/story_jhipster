package com.jher.nid_aux_histoires.service.dto.idea_generator;

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

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getLandscape() {
		return landscape;
	}

	public void setLandscape(String landscape) {
		this.landscape = landscape;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
}
