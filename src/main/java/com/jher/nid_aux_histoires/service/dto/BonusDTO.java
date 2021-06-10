package com.jher.nid_aux_histoires.service.dto;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Comment} entity.
 */
public class BonusDTO implements Serializable, Comparable<BonusDTO> {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private byte[] data;

	private String extension;

	private Long bookId;

	private String description;

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

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public void setFile(MultipartFile file) throws IOException {
		this.setData(file.getBytes());
		this.setName(FilenameUtils.getBaseName(file.getOriginalFilename()));
		this.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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
		return "BonusDTO{" + "id=" + getId() + ", name='" + getName() + "'" + ", extension='" + getExtension() + "'"
				+ "}";
	}

	@Override
	public int compareTo(BonusDTO o) {
		return this.id.compareTo(o.id);
	}
}
