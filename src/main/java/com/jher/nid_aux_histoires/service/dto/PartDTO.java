package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Part} entity.
 */
public class PartDTO implements Serializable, Comparable<PartDTO> {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String description;

	private Integer number;

	private Set<ChapterDTO> chapters = new HashSet<>();

	private Long bookId;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Set<ChapterDTO> getChapters() {
		return chapters;
	}

	public void setChapters(Set<ChapterDTO> chapters) {
		this.chapters = new TreeSet<ChapterDTO>();
		this.chapters.addAll(chapters);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PartDTO)) {
			return false;
		}

		return id != null && id.equals(((PartDTO) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "PartDTO{" + "id=" + getId() + ", name='" + getName() + "'" + ", description='" + getDescription() + "'"
				+ ", number=" + getNumber() + ", bookId=" + getBookId() + "}";
	}

	@Override
	public int compareTo(PartDTO o) {
		return this.number.compareTo(o.number);
	}
}
