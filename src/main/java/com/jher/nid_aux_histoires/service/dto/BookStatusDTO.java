package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.BookStatus} entity.
 */
@Data
public class BookStatusDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private Boolean finished;

	private Boolean favorit;

	private Long bookId;

	private Long curentChapterId;

	private Long userId;

	private Boolean isFavorit() {
		return favorit;
	}

	private Boolean isFinished() {
		return finished;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof BookStatusDTO)) {
			return false;
		}

		return id != null && id.equals(((BookStatusDTO) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "BookStatusDTO{" + "id=" + getId() + ", finished='" + isFinished() + "'" + ", favorit='" + isFavorit()
				+ "'" + ", bookId=" + getBookId() + ", curentChapterId=" + getCurentChapterId() + "}";
	}
}
