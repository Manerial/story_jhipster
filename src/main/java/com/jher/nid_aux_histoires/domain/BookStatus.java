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

/**
 * A BookStatus.
 */
@Entity
@Table(name = "book_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean isFinished() {
		return finished;
	}

	public BookStatus finished(Boolean finished) {
		this.finished = finished;
		return this;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	public Boolean isFavorit() {
		return favorit;
	}

	public BookStatus favorit(Boolean favorit) {
		this.favorit = favorit;
		return this;
	}

	public void setFavorit(Boolean favorit) {
		this.favorit = favorit;
	}

	public Book getBook() {
		return book;
	}

	public BookStatus book(Book book) {
		this.book = book;
		return this;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Chapter getCurentChapter() {
		return curentChapter;
	}

	public BookStatus curentChapter(Chapter chapter) {
		this.curentChapter = chapter;
		return this;
	}

	public void setCurentChapter(Chapter chapter) {
		this.curentChapter = chapter;
	}

	public User getUser() {
		return user;
	}

	public BookStatus user(User user) {
		this.user = user;
		return this;
	}

	public void setUser(User user) {
		this.user = user;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

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
