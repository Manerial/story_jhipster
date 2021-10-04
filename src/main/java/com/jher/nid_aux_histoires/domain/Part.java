package com.jher.nid_aux_histoires.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * A Part.
 */
@Entity
@Table(name = "part")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Part implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "number")
	private Integer number;

	@OneToMany(mappedBy = "part", fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Chapter> chapters = new HashSet<>();

	@ManyToOne
	@JsonIgnoreProperties(value = "parts", allowSetters = true)
	private Book book;

	public Part name(String name) {
		this.name = name;
		return this;
	}

	public Part description(String description) {
		this.description = description;
		return this;
	}

	public Part number(Integer number) {
		this.number = number;
		return this;
	}

	public Part chapters(Set<Chapter> chapters) {
		this.chapters = chapters;
		return this;
	}

	public Part addChapter(Chapter chapter) {
		this.chapters.add(chapter);
		chapter.setPart(this);
		return this;
	}

	public Part removeChapter(Chapter chapter) {
		this.chapters.remove(chapter);
		chapter.setPart(null);
		return this;
	}

	public Part book(Book book) {
		this.book = book;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Part)) {
			return false;
		}
		return id != null && id.equals(((Part) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Part{" + "id=" + getId() + ", name='" + getName() + "'" + ", description='" + getDescription() + "'"
				+ ", number=" + getNumber() + "}";
	}
}
