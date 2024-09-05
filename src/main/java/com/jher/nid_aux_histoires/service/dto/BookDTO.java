package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import lombok.Data;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Book} entity.
 */
@Data
public class BookDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Long authorId;

	private String authorLogin;

	private String description;

	private Boolean visibility;

	private Set<PartDTO> parts = new HashSet<>();

	private Set<CommentDTO> comments = new HashSet<>();

	private byte[] coverPreview;

	private Long coverId;

	private boolean bonuses = false;

	public void setParts(Set<PartDTO> parts) {
		this.parts = new TreeSet<PartDTO>();
		this.parts.addAll(parts);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof BookDTO)) {
			return false;
		}

		return id != null && id.equals(((BookDTO) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "BookDTO{" + "id=" + getId() + ", name='" + getName() + "'" + ", author='" + getAuthorLogin() + "'"
				+ ", coverId=" + getCoverId() + "}";
	}
}
