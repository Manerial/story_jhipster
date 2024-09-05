package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Comment} entity.
 */
@Data
public class BonusDTO implements Serializable, Comparable<BonusDTO> {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Long ownerId;

	private String ownerLogin;

	private byte[] data;

	private String dataContentType;

	private Long bookId;

	private String description;

	private boolean isImage;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof BonusDTO)) {
			return false;
		}

		return id != null && id.equals(((BonusDTO) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "BonusDTO{" + "id=" + getId() + ", name='" + getName() + "'" + ", dataContentType='"
				+ getDataContentType() + "'" + "}";
	}

	@Override
	public int compareTo(BonusDTO o) {
		return this.id.compareTo(o.id);
	}
}
