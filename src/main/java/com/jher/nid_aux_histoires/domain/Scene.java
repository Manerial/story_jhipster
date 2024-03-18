package com.jher.nid_aux_histoires.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Scene.
 */
@Entity
@Table(name = "scene")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Scene implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private Integer number;

    @Lob
    @Column(name = "text")
    private String text;

    @Column(name = "timestamp_start")
    private LocalDate timestampStart;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rel_scene__image", joinColumns = @JoinColumn(name = "scene_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "bookToCovers", "books", "parts", "chapters", "scenes" }, allowSetters = true)
    private Set<Image> images = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "scenes", "images", "part", "bookStatuses" }, allowSetters = true)
    private Chapter chapter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Scene id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Scene name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return this.number;
    }

    public Scene number(Integer number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getText() {
        return this.text;
    }

    public Scene text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getTimestampStart() {
        return this.timestampStart;
    }

    public Scene timestampStart(LocalDate timestampStart) {
        this.setTimestampStart(timestampStart);
        return this;
    }

    public void setTimestampStart(LocalDate timestampStart) {
        this.timestampStart = timestampStart;
    }

    public Set<Image> getImages() {
        return this.images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Scene images(Set<Image> images) {
        this.setImages(images);
        return this;
    }

    public Scene addImage(Image image) {
        this.images.add(image);
        return this;
    }

    public Scene removeImage(Image image) {
        this.images.remove(image);
        return this;
    }

    public Chapter getChapter() {
        return this.chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Scene chapter(Chapter chapter) {
        this.setChapter(chapter);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Scene)) {
            return false;
        }
        return getId() != null && getId().equals(((Scene) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Scene{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", number=" + getNumber() +
            ", text='" + getText() + "'" +
            ", timestampStart='" + getTimestampStart() + "'" +
            "}";
    }
}
