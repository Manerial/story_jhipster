package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Book} entity.
 */
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

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getAuthorLogin() {
		return authorLogin;
	}

	public void setAuthorLogin(String authorLogin) {
		this.authorLogin = authorLogin;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getVisibility() {
		return visibility;
	}

	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}

	public Long getCoverId() {
		return coverId;
	}

	public void setCoverId(Long coverId) {
		this.coverId = coverId;
	}

	public byte[] getCoverPreview() {
		return coverPreview;
	}

	public void setCoverPreview(byte[] coverPreview) {
		this.coverPreview = coverPreview;
	}

	public Set<PartDTO> getParts() {
		return parts;
	}

	public void setParts(Set<PartDTO> parts) {
		this.parts = new TreeSet<PartDTO>();
		this.parts.addAll(parts);
	}

	public Set<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDTO> comments) {
		this.comments = comments;
	}

	public boolean getBonuses() {
		return bonuses;
	}

	public void setBonuses(boolean bonuses) {
		this.bonuses = bonuses;
	}

	public void setBonuses(Set<BonusDTO> bonuses) {
		this.bonuses = bonuses.size() > 0;
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
