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

/**
 * A Scene.
 */
@Entity
@Table(name = "scene")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Scene name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public Scene number(Integer number) {
		this.number = number;
		return this;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getText() {
		return text;
	}

	public Scene text(String text) {
		this.text = text;
		return this;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getTimestampStart() {
		return timestampStart;
	}

	public Scene timestampStart(Date timestampStart) {
		this.timestampStart = timestampStart;
		return this;
	}

	public void setTimestampStart(Date timestampStart) {
		this.timestampStart = timestampStart;
	}

	public Chapter getChapter() {
		return chapter;
	}

	public Scene chapter(Chapter chapter) {
		this.chapter = chapter;
		return this;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

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
