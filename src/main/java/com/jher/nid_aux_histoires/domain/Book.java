package com.jher.nid_aux_histoires.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "chapters", "images", "book" }, allowSetters = true)
    private Set<Part> parts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rel_book__image", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "bookToCovers", "books", "parts", "chapters", "scenes" }, allowSetters = true)
    private Set<Image> images = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "bookToCovers", "books", "parts", "chapters", "scenes" }, allowSetters = true)
    private Image cover;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "book", "curentChapter" }, allowSetters = true)
    private Set<BookStatus> bookStatuses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Book id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Book name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return this.author;
    }

    public Book author(String author) {
        this.setAuthor(author);
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<Part> getParts() {
        return this.parts;
    }

    public void setParts(Set<Part> parts) {
        if (this.parts != null) {
            this.parts.forEach(i -> i.setBook(null));
        }
        if (parts != null) {
            parts.forEach(i -> i.setBook(this));
        }
        this.parts = parts;
    }

    public Book parts(Set<Part> parts) {
        this.setParts(parts);
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

    public Set<Image> getImages() {
        return this.images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Book images(Set<Image> images) {
        this.setImages(images);
        return this;
    }

    public Book addImage(Image image) {
        this.images.add(image);
        return this;
    }

    public Book removeImage(Image image) {
        this.images.remove(image);
        return this;
    }

    public Image getCover() {
        return this.cover;
    }

    public void setCover(Image image) {
        this.cover = image;
    }

    public Book cover(Image image) {
        this.setCover(image);
        return this;
    }

    public Set<BookStatus> getBookStatuses() {
        return this.bookStatuses;
    }

    public void setBookStatuses(Set<BookStatus> bookStatuses) {
        if (this.bookStatuses != null) {
            this.bookStatuses.forEach(i -> i.setBook(null));
        }
        if (bookStatuses != null) {
            bookStatuses.forEach(i -> i.setBook(this));
        }
        this.bookStatuses = bookStatuses;
    }

    public Book bookStatuses(Set<BookStatus> bookStatuses) {
        this.setBookStatuses(bookStatuses);
        return this;
    }

    public Book addBookStatus(BookStatus bookStatus) {
        this.bookStatuses.add(bookStatus);
        bookStatus.setBook(this);
        return this;
    }

    public Book removeBookStatus(BookStatus bookStatus) {
        this.bookStatuses.remove(bookStatus);
        bookStatus.setBook(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return getId() != null && getId().equals(((Book) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", author='" + getAuthor() + "'" +
            "}";
    }
}
