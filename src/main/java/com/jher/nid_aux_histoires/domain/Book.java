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

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "visibility")
	private Boolean visibility;

	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Part> parts = new HashSet<>();

	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Comment> comments = new HashSet<>();

	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Bonus> bonuses = new HashSet<>();

	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Library> libraries = new HashSet<>();

	@ManyToOne
	@JsonIgnoreProperties(value = "picture", allowSetters = true)
	private Cover cover;

	@ManyToOne
	@JsonIgnoreProperties(value = "books", allowSetters = true)
	private User author;

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

	public Book name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getAuthor() {
		return author;
	}

	public Book author(User author) {
		this.author = author;
		return this;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public Book description(String description) {
		this.description = description;
		return this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getVisibility() {
		return visibility;
	}

	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}

	public Set<Part> getParts() {
		return parts;
	}

	public Book parts(Set<Part> parts) {
		this.parts = parts;
		return this;
	}

	public Book addPart(Part part) {
		this.parts.add(part);
		part.setBook(this);
		return this;
	}

	public Book removePart(Part part) {
		this.parts.remove(part);
		part.setBook(null);
		return this;
	}

	public void setParts(Set<Part> parts) {
		this.parts = parts;
	}

	public Cover getCover() {
		return cover;
	}

	public Book cover(Cover cover) {
		this.cover = cover;
		return this;
	}

	public void setCover(Cover cover) {
		this.cover = cover;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public Book comments(Set<Comment> comments) {
		this.comments = comments;
		return this;
	}

	public Book addComment(Comment comment) {
		this.comments.add(comment);
		comment.setBook(this);
		return this;
	}

	public Book removeComment(Comment comment) {
		this.comments.remove(comment);
		comment.setBook(null);
		return this;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Bonus> getBonuses() {
		return bonuses;
	}

	public Book bonuses(Set<Bonus> bonuses) {
		this.bonuses = bonuses;
		return this;
	}

	public Book addBonus(Bonus comment) {
		this.bonuses.add(comment);
		comment.setBook(this);
		return this;
	}

	public Book removeBonus(Bonus comment) {
		this.bonuses.remove(comment);
		comment.setBook(null);
		return this;
	}

	public void setBonuses(Set<Bonus> bonuses) {
		this.bonuses = bonuses;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Book)) {
			return false;
		}
		return id != null && id.equals(((Book) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Book{" + "id=" + getId() + ", name='" + getName() + "'" + ", author='" + getAuthor() + "'" + "}";
	}
}
