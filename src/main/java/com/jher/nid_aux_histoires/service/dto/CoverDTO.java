package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;

import javax.persistence.Lob;

import lombok.Data;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Cover} entity.
 */
@Data
public class CoverDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Long ownerId;

	private String ownerLogin;

	@Lob
	private byte[] picture;

	private String pictureContentType;

	@Lob
	private byte[] preview;

	private String previewContentType;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CoverDTO)) {
			return false;
		}

		return id != null && id.equals(((CoverDTO) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "CoverDTO{" + "id=" + getId() + ", name='" + getName() + "'" + ", picture='" + getPicture() + "'"
				+ ", preview='" + getPreview() + "'" + "}";
	}
}
