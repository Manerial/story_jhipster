package com.jher.nid_aux_histoires.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * A Scene.
 */
@Entity
@Table(name = "scene")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Scene implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "number")
	private Integer number;

	@Lob
	@Column(name = "text")
	private String text;

	@Column(name = "timestamp_start")
	private Date timestampStart;

	@ManyToOne
	@JsonIgnoreProperties(value = "scenes", allowSetters = true)
	private Chapter chapter;

	public Scene name(String name) {
		this.name = name;
		return this;
	}

	public Scene number(Integer number) {
		this.number = number;
		return this;
	}

	public Scene text(String text) {
		this.text = text;
		return this;
	}

	public Scene timestampStart(Date timestampStart) {
		this.timestampStart = timestampStart;
		return this;
	}

	public Scene chapter(Chapter chapter) {
		this.chapter = chapter;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Scene)) {
			return false;
		}
		return id != null && id.equals(((Scene) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Scene{" + "id=" + getId() + ", name='" + getName() + "'" + ", number=" + getNumber() + ", text='"
				+ getText() + "'" + ", timestampStart='" + getTimestampStart() + "'" + "}";
	}
}
