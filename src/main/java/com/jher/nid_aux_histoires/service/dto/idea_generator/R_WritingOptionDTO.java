package com.jher.nid_aux_histoires.service.dto.idea_generator;

import lombok.Data;

@Data
public class R_WritingOptionDTO implements Random_Interface {
	private String style;
	private String theme;

	public R_WritingOptionDTO() {
	}

	public R_WritingOptionDTO(R_WritingOptionDTO writingOption) {
		setStyle(writingOption.getStyle());
		setTheme(writingOption.getTheme());
	}
}
