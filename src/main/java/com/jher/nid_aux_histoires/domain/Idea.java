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
 * A Idea.
 */
@Entity
@Table(name = "idea")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Idea implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "type")
	private String type;

	@Column(name = "value")
	private String value;

	@Column(name = "complement")
	private String complement;

	public Idea type(String type) {
		this.type = type;
		return this;
	}

	public Idea value(String value) {
		this.value = value;
		return this;
	}

	public Idea complement(String complement) {
		this.complement = complement;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Idea)) {
			return false;
		}
		return id != null && id.equals(((Idea) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Idea{" + "id=" + getId() + ", type='" + getType() + "'" + ", value='" + getValue() + "'"
				+ ", complement='" + getComplement() + "'" + "}";
	}
}
