package com.jher.nid_aux_histoires.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

/**
 * A WordAnalysis.
 */
@Entity
@Table(name = "word_analysis")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class WordAnalysis implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "type")
	private String type;

	@Column(name = "name")
	private String name;

	@Column(name = "analysis", columnDefinition = "CLOB")
	private String analysis;

	public WordAnalysis type(String type) {
		this.type = type;
		return this;
	}

	public WordAnalysis name(String name) {
		this.name = name;
		return this;
	}

	public WordAnalysis analysis(String analysis) {
		this.analysis = analysis;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof WordAnalysis)) {
			return false;
		}
		return id != null && id.equals(((WordAnalysis) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "WordAnalysis{" + "id=" + getId() + ", type='" + getType() + "'" + ", name='" + getName() + "'"
				+ ", analysis='" + getAnalysis() + "'" + "}";
	}
}
