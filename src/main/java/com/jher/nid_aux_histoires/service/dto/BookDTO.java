package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Book} entity.
 */
public class BookDTO implements Serializable {

	private Long id;

	private String name;

	private String author;

	private String description;

	private Set<ImageDTO> images = new HashSet<>();

	private Set<PartDTO> parts = new HashSet<>();

	private Set<CommentDTO> comments = new HashSet<>();

	private byte[] coverPreview;

	private Long coverId;

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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<ImageDTO> getImages() {
		return images;
	}

	public void setImages(Set<ImageDTO> images) {
		this.images = images;
	}

	public Long getCoverId() {
		return coverId;
	}

	public void setCoverId(Long imageId) {
		this.coverId = imageId;
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
		return "BookDTO{" + "id=" + getId() + ", name='" + getName() + "'" + ", author='" + getAuthor() + "'"
				+ ", images='" + getImages() + "'" + ", coverId=" + getCoverId() + "}";
	}
}
