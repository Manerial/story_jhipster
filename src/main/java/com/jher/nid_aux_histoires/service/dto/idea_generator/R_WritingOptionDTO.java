package com.jher.nid_aux_histoires.service.dto.idea_generator;

public class R_WritingOptionDTO implements Random_Interface {
	private String style;
	private String theme;

	public R_WritingOptionDTO() {
	}

	public R_WritingOptionDTO(R_WritingOptionDTO writingOption) {
		setStyle(writingOption.getStyle());
		setTheme(writingOption.getTheme());
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
}
