package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.BookStatus} entity.
 */
public class BookStatusDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private Boolean finished;

	private Boolean favorit;

	private Long bookId;

	private Long curentChapterId;

	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean isFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	public Boolean isFavorit() {
		return favorit;
	}

	public void setFavorit(Boolean favorit) {
		this.favorit = favorit;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Long getCurentChapterId() {
		return curentChapterId;
	}

	public void setCurentChapterId(Long chapterId) {
		this.curentChapterId = chapterId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
		return "BookStatusDTO{" + "id=" + getId() + ", finished='" + isFinished() + "'" + ", favorit='" + isFavorit() + "'"
				+ ", bookId=" + getBookId() + ", curentChapterId=" + getCurentChapterId() + "}";
	}
}
