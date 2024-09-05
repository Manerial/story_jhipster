package com.jher.nid_aux_histoires.domain;

import java.io.Serializable;

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
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Bonus.
 */
@Entity
@Table(name = "bonus")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bonus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Lob
	@Column(name = "data")
	private byte[] data;

	@Column(name = "extension")
	private String extension;

	@ManyToOne
	@JsonIgnoreProperties(value = "bonuses", allowSetters = true)
	private Book book;

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

	public Bonus name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getData() {
		return data;
	}

	public Bonus data(byte[] data) {
		this.data = data;
		return this;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getExtension() {
		return extension;
	}

	public Bonus extension(String extension) {
		this.extension = extension;
		return this;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Book getBook() {
		return book;
	}

	public Bonus book(Book book) {
		this.book = book;
		return this;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Bonus)) {
			return false;
		}
		return id != null && id.equals(((Bonus) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Bonus{" + "id=" + getId() + ", name='" + getName() + "'" + "}";
	}
}
