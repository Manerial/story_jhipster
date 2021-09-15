package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Lob;

import lombok.Data;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Scene} entity.
 */
@Data
public class SceneDTO implements Serializable, Comparable<SceneDTO> {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Integer number;

	@Lob
	private String text;

	private Date timestampStart;

	private Long chapterId;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof SceneDTO)) {
			return false;
		}

		return id != null && id.equals(((SceneDTO) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "SceneDTO{" + "id=" + getId() + ", name='" + getName() + "'" + ", number=" + getNumber() + ", text='"
				+ getText() + "'" + ", timestampStart='" + getTimestampStart() + "'" + ", chapterId=" + getChapterId()
				+ "}";
	}

	@Override
	public int compareTo(SceneDTO o) {
		return this.number.compareTo(o.number);
	}
}
