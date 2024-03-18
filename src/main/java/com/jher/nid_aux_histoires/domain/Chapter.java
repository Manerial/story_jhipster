package com.jher.nid_aux_histoires.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Chapter.
 */
@Entity
@Table(name = "chapter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Chapter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "number")
    private Integer number;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chapter")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "images", "chapter" }, allowSetters = true)
    private Set<Scene> scenes = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_chapter__image",
        joinColumns = @JoinColumn(name = "chapter_id"),
        inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "bookToCovers", "books", "parts", "chapters", "scenes" }, allowSetters = true)
    private Set<Image> images = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "chapters", "images", "book" }, allowSetters = true)
    private Part part;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "curentChapter")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "book", "curentChapter" }, allowSetters = true)
    private Set<BookStatus> bookStatuses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Chapter id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Chapter name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Chapter description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumber() {
        return this.number;
    }

    public Chapter number(Integer number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Set<Scene> getScenes() {
        return this.scenes;
    }

    public void setScenes(Set<Scene> scenes) {
        if (this.scenes != null) {
            this.scenes.forEach(i -> i.setChapter(null));
        }
        if (scenes != null) {
            scenes.forEach(i -> i.setChapter(this));
        }
        this.scenes = scenes;
    }

    public Chapter scenes(Set<Scene> scenes) {
        this.setScenes(scenes);
        return this;
    }

    public Chapter addScene(Scene scene) {
        this.scenes.add(scene);
        scene.setChapter(this);
        return this;
    }

    public Chapter removeScene(Scene scene) {
        this.scenes.remove(scene);
        scene.setChapter(null);
        return this;
    }

    public Set<Image> getImages() {
        return this.images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Chapter images(Set<Image> images) {
        this.setImages(images);
        return this;
    }

    public Chapter addImage(Image image) {
        this.images.add(image);
        return this;
    }

    public Chapter removeImage(Image image) {
        this.images.remove(image);
        return this;
    }

    public Part getPart() {
        return this.part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Chapter part(Part part) {
        this.setPart(part);
        return this;
    }

    public Set<BookStatus> getBookStatuses() {
        return this.bookStatuses;
    }

    public void setBookStatuses(Set<BookStatus> bookStatuses) {
        if (this.bookStatuses != null) {
            this.bookStatuses.forEach(i -> i.setCurentChapter(null));
        }
        if (bookStatuses != null) {
            bookStatuses.forEach(i -> i.setCurentChapter(this));
        }
        this.bookStatuses = bookStatuses;
    }

    public Chapter bookStatuses(Set<BookStatus> bookStatuses) {
        this.setBookStatuses(bookStatuses);
        return this;
    }

    public Chapter addBookStatus(BookStatus bookStatus) {
        this.bookStatuses.add(bookStatus);
        bookStatus.setCurentChapter(this);
        return this;
    }

    public Chapter removeBookStatus(BookStatus bookStatus) {
        this.bookStatuses.remove(bookStatus);
        bookStatus.setCurentChapter(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chapter)) {
            return false;
        }
        return getId() != null && getId().equals(((Chapter) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Chapter{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", number=" + getNumber() +
            "}";
    }
}
