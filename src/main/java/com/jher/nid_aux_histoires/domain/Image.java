package com.jher.nid_aux_histoires.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Image.
 */
@Entity
@Table(name = "image")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    @Lob
    @Column(name = "preview")
    private byte[] preview;

    @Column(name = "preview_content_type")
    private String previewContentType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cover")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parts", "images", "cover", "bookStatuses" }, allowSetters = true)
    private Set<Book> bookToCovers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "images")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parts", "images", "cover", "bookStatuses" }, allowSetters = true)
    private Set<Book> books = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "images")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "chapters", "images", "book" }, allowSetters = true)
    private Set<Part> parts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "images")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "scenes", "images", "part", "bookStatuses" }, allowSetters = true)
    private Set<Chapter> chapters = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "images")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "images", "chapter" }, allowSetters = true)
    private Set<Scene> scenes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Image id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Image name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return this.picture;
    }

    public Image picture(byte[] picture) {
        this.setPicture(picture);
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return this.pictureContentType;
    }

    public Image pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public byte[] getPreview() {
        return this.preview;
    }

    public Image preview(byte[] preview) {
        this.setPreview(preview);
        return this;
    }

    public void setPreview(byte[] preview) {
        this.preview = preview;
    }

    public String getPreviewContentType() {
        return this.previewContentType;
    }

    public Image previewContentType(String previewContentType) {
        this.previewContentType = previewContentType;
        return this;
    }

    public void setPreviewContentType(String previewContentType) {
        this.previewContentType = previewContentType;
    }

    public Set<Book> getBookToCovers() {
        return this.bookToCovers;
    }

    public void setBookToCovers(Set<Book> books) {
        if (this.bookToCovers != null) {
            this.bookToCovers.forEach(i -> i.setCover(null));
        }
        if (books != null) {
            books.forEach(i -> i.setCover(this));
        }
        this.bookToCovers = books;
    }

    public Image bookToCovers(Set<Book> books) {
        this.setBookToCovers(books);
        return this;
    }

    public Image addBookToCover(Book book) {
        this.bookToCovers.add(book);
        book.setCover(this);
        return this;
    }

    public Image removeBookToCover(Book book) {
        this.bookToCovers.remove(book);
        book.setCover(null);
        return this;
    }

    public Set<Book> getBooks() {
        return this.books;
    }

    public void setBooks(Set<Book> books) {
        if (this.books != null) {
            this.books.forEach(i -> i.removeImage(this));
        }
        if (books != null) {
            books.forEach(i -> i.addImage(this));
        }
        this.books = books;
    }

    public Image books(Set<Book> books) {
        this.setBooks(books);
        return this;
    }

    public Image addBook(Book book) {
        this.books.add(book);
        book.getImages().add(this);
        return this;
    }

    public Image removeBook(Book book) {
        this.books.remove(book);
        book.getImages().remove(this);
        return this;
    }

    public Set<Part> getParts() {
        return this.parts;
    }

    public void setParts(Set<Part> parts) {
        if (this.parts != null) {
            this.parts.forEach(i -> i.removeImage(this));
        }
        if (parts != null) {
            parts.forEach(i -> i.addImage(this));
        }
        this.parts = parts;
    }

    public Image parts(Set<Part> parts) {
        this.setParts(parts);
        return this;
    }

    public Image addPart(Part part) {
        this.parts.add(part);
        part.getImages().add(this);
        return this;
    }

    public Image removePart(Part part) {
        this.parts.remove(part);
        part.getImages().remove(this);
        return this;
    }

    public Set<Chapter> getChapters() {
        return this.chapters;
    }

    public void setChapters(Set<Chapter> chapters) {
        if (this.chapters != null) {
            this.chapters.forEach(i -> i.removeImage(this));
        }
        if (chapters != null) {
            chapters.forEach(i -> i.addImage(this));
        }
        this.chapters = chapters;
    }

    public Image chapters(Set<Chapter> chapters) {
        this.setChapters(chapters);
        return this;
    }

    public Image addChapter(Chapter chapter) {
        this.chapters.add(chapter);
        chapter.getImages().add(this);
        return this;
    }

    public Image removeChapter(Chapter chapter) {
        this.chapters.remove(chapter);
        chapter.getImages().remove(this);
        return this;
    }

    public Set<Scene> getScenes() {
        return this.scenes;
    }

    public void setScenes(Set<Scene> scenes) {
        if (this.scenes != null) {
            this.scenes.forEach(i -> i.removeImage(this));
        }
        if (scenes != null) {
            scenes.forEach(i -> i.addImage(this));
        }
        this.scenes = scenes;
    }

    public Image scenes(Set<Scene> scenes) {
        this.setScenes(scenes);
        return this;
    }

    public Image addScene(Scene scene) {
        this.scenes.add(scene);
        scene.getImages().add(this);
        return this;
    }

    public Image removeScene(Scene scene) {
        this.scenes.remove(scene);
        scene.getImages().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }
        return getId() != null && getId().equals(((Image) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Image{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            ", preview='" + getPreview() + "'" +
            ", previewContentType='" + getPreviewContentType() + "'" +
            "}";
    }
}
