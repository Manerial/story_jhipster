package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Idea} entity.
 */
@Data
public class IdeaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String type;

	private String value;

	private String complement;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof IdeaDTO)) {
			return false;
		}

		return id != null && id.equals(((IdeaDTO) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "IdeaDTO{" + "id=" + getId() + ", type='" + getType() + "'" + ", value='" + getValue() + "'"
				+ ", complement='" + getComplement() + "'" + "}";
	}
}
