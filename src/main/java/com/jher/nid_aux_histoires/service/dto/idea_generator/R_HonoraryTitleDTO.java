package com.jher.nid_aux_histoires.service.dto.idea_generator;

public class R_HonoraryTitleDTO implements Random_Interface {
	private String title;

	public R_HonoraryTitleDTO(String title) {
		this.setTitle(title);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
