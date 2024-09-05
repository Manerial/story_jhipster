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
 * A Chapter.
 */
@Entity
@Table(name = "chapter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Chapter implements Serializable {

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

	@OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Scene> scenes = new HashSet<>();

	@OneToMany(mappedBy = "curentChapter", fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<BookStatus> bookStatuses = new HashSet<>();

	@ManyToOne
	@JsonIgnoreProperties(value = "chapters", allowSetters = true)
	private Part part;

	public Chapter name(String name) {
		this.name = name;
		return this;
	}

	public Chapter description(String description) {
		this.description = description;
		return this;
	}

	public Chapter number(Integer number) {
		this.number = number;
		return this;
	}

	public Chapter scenes(Set<Scene> scenes) {
		this.scenes = scenes;
		return this;
	}

	public Chapter addScene(Scene scene) {
		this.scenes.add(scene);
		scene.setChapter(this);
		return this;
	}

	public Chapter removeScene(Scene scene) {
		this.scenes.remove(scene);
		scene.setChapter(null);
		return this;
	}

	public Chapter part(Part part) {
		this.part = part;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Chapter)) {
			return false;
		}
		return id != null && id.equals(((Chapter) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Chapter{" + "id=" + getId() + ", name='" + getName() + "'" + ", description='" + getDescription() + "'"
				+ ", number=" + getNumber() + "}";
	}
}
