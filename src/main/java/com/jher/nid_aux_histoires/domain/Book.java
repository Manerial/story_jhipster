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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

	@Column(name = "author")
	private String author;

	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Part> parts = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JoinTable(name = "book_image", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
	private Set<Image> images = new HashSet<>();

	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Comment> comments = new HashSet<>();

	@ManyToOne
	@JsonIgnoreProperties(value = "picture", allowSetters = true)
	private Image cover;

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

	public String getAuthor() {
		return author;
	}

	public Book author(String author) {
		this.author = author;
		return this;
	}

	public void setAuthor(String author) {
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

	public Set<Image> getImages() {
		return images;
	}

	public Book images(Set<Image> images) {
		this.images = images;
		return this;
	}

	public Book addImage(Image image) {
		this.images.add(image);
		image.getBooks().add(this);
		return this;
	}

	public Book removeImage(Image image) {
		this.images.remove(image);
		image.getBooks().remove(this);
		return this;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public Image getCover() {
		return cover;
	}

	public Book cover(Image image) {
		this.cover = image;
		return this;
	}

	public void setCover(Image image) {
		this.cover = image;
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
