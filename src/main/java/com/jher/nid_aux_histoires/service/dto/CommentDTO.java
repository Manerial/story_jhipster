package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Comment} entity.
 */
public class CommentDTO implements Serializable, Comparable<CommentDTO> {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String text;

	private Date createdDate;

	private Long bookId;

	private String userLogin;

	private Long userId;

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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
