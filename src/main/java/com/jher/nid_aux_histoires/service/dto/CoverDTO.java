package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;

import javax.persistence.Lob;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Cover} entity.
 */
public class CoverDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	@Lob
	private byte[] picture;

	private String pictureContentType;

	@Lob
	private byte[] preview;

	private String previewContentType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public String getPictureContentType() {
		return pictureContentType;
	}

	public void setPictureContentType(String pictureContentType) {
		this.pictureContentType = pictureContentType;
	}

	public byte[] getPreview() {
		return preview;
	}

	public void setPreview(byte[] preview) {
		this.preview = preview;
	}

	public String getPreviewContentType() {
		return previewContentType;
	}

	public void setPreviewContentType(String previewContentType) {
		this.previewContentType = previewContentType;
	}

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
