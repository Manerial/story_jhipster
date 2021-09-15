package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Comment} entity.
 */
@Data
public class CommentDTO implements Serializable, Comparable<CommentDTO> {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String text;

	private Date createdDate;

	private Long bookId;

	private String userLogin;

	private Long userId;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CommentDTO)) {
			return false;
		}

		return id != null && id.equals(((CommentDTO) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "CommentDTO{" + "id=" + getId() + ", text='" + getText() + "'" + "}";
	}

	@Override
	public int compareTo(CommentDTO o) {
		return this.id.compareTo(o.id);
	}
}
