package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.WordAnalysis} entity.
 */
@Data
public class WordAnalysisDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String type;

	private String name;

	private String analysis;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof WordAnalysisDTO)) {
			return false;
		}

		return id != null && id.equals(((WordAnalysisDTO) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "WordAnalysisDTO{" + "id=" + getId() + ", type='" + getType() + "'" + ", name='" + getName() + "'"
				+ ", analysis='" + getAnalysis() + "'" + "}";
	}
}
