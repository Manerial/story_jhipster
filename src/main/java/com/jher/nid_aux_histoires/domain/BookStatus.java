package com.jher.nid_aux_histoires.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * A BookStatus.
 */
@Entity
@Table(name = "book_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class BookStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "finished")
	private Boolean finished;

	@Column(name = "favorit")
	private Boolean favorit;

	@ManyToOne
	@JsonIgnoreProperties(value = "bookStatus", allowSetters = true)
	private Book book;

	@ManyToOne
	@JsonIgnoreProperties(value = "bookStatus", allowSetters = true)
	private Chapter curentChapter;

	@ManyToOne
	@JsonIgnoreProperties(value = "bookStatuses", allowSetters = true)
	private User user;

	public Boolean isFinished() {
		return finished;
	}

	public BookStatus finished(Boolean finished) {
		this.finished = finished;
		return this;
	}

	public Boolean isFavorit() {
		return favorit;
	}

	public BookStatus favorit(Boolean favorit) {
		this.favorit = favorit;
		return this;
	}

	public BookStatus book(Book book) {
		this.book = book;
		return this;
	}

	public BookStatus curentChapter(Chapter chapter) {
		this.curentChapter = chapter;
		return this;
	}

	public BookStatus user(User user) {
		this.user = user;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof BookStatus)) {
			return false;
		}
		return id != null && id.equals(((BookStatus) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "BookStatus{" + "id=" + getId() + ", finished='" + isFinished() + "'" + ", favorit='" + isFavorit() + "'"
				+ "}";
	}
}
