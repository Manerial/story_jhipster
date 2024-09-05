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

import lombok.Data;

/**
 * A Bonus.
 */
@Entity
@Table(name = "bonus")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
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

	@Column(name = "data_content_type")
	private String dataContentType;

	@ManyToOne
	@JsonIgnoreProperties(value = "bonuses", allowSetters = true)
	private Book book;

	@Column(name = "description")
	private String description;

	@ManyToOne
	@JsonIgnoreProperties(value = "bonus", allowSetters = true)
	private User owner;

	public Bonus name(String name) {
		this.name = name;
		return this;
	}

	public Bonus data(byte[] data) {
		this.data = data;
		return this;
	}

	public Bonus dataContentType(String dataContentType) {
		this.dataContentType = dataContentType;
		return this;
	}

	public Bonus book(Book book) {
		this.book = book;
		return this;
	}

	public Bonus description(String description) {
		this.description = description;
		return this;
	}

	public Bonus owner(User owner) {
		this.owner = owner;
		return this;
	}

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
