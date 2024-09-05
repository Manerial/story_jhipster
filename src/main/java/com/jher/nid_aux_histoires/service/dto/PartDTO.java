package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import lombok.Data;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Part} entity.
 */
@Data
public class PartDTO implements Serializable, Comparable<PartDTO> {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String description;

	private Integer number;

	private Set<ChapterDTO> chapters = new HashSet<>();

	private Long bookId;

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
