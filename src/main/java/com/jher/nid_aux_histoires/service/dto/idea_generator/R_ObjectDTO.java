package com.jher.nid_aux_histoires.service.dto.idea_generator;

import lombok.Data;

@Data
public class R_ObjectDTO implements Random_Interface {
	private String object;
	private String adjective;
	private String suffix;

	public R_ObjectDTO() {
	}

	public R_ObjectDTO(R_ObjectDTO object) {
		setObject(object.getObject());
		setAdjective(object.getAdjective());
		setSuffix(object.getSuffix());
	}
}