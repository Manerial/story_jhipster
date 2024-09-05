package com.jher.nid_aux_histoires.service.dto.idea_generator;

import lombok.Data;

@Data
public class R_HonoraryTitleDTO implements Random_Interface {
	private String title;

	public R_HonoraryTitleDTO(String title) {
		setTitle(title);
	}
}
