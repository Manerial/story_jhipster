package com.jher.nid_aux_histoires.service.dto.idea_generator;

public class WritingOptionDTO {
	private String style;
	private String theme;

	public WritingOptionDTO() {
	}

	public WritingOptionDTO(WritingOptionDTO writingOption) {
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
