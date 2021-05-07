package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Comment} entity.
 */
public class CommentDTO implements Serializable, Comparable<CommentDTO> {

	private Long id;

	private String text;

	private Long bookId;

	private String userLogin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

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
