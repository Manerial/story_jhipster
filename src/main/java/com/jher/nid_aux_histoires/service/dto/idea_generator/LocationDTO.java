package com.jher.nid_aux_histoires.service.dto.idea_generator;

public class LocationDTO {
	private String place;
	private String landscape;
	private String material;

	public LocationDTO() {
	}

	public LocationDTO(LocationDTO location) {
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
