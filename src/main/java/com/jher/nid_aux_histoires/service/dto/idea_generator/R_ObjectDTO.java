package com.jher.nid_aux_histoires.service.dto.idea_generator;

public class R_ObjectDTO implements Random_Interface {
	private String object;
	private String adjective;
	private String suffix;

	public R_ObjectDTO() {
	}

	public R_ObjectDTO(R_ObjectDTO location) {
		setObject(location.getObject());
		setAdjective(location.getAdjective());
		setSuffix(location.getSuffix());
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getAdjective() {
		return adjective;
	}

	public void setAdjective(String adjective) {
		this.adjective = adjective;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
