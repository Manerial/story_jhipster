package com.jher.nid_aux_histoires.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Scene.
 */
@Entity
@Table(name = "scene")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Scene implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "scene_image",
               joinColumns = @JoinColumn(name = "scene_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
    private Set<Image> images = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "scenes", allowSetters = true)
    private Chapter chapter;

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

    public Scene name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public Scene number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public Scene text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getTimestampStart() {
        return timestampStart;
    }

    public Scene timestampStart(LocalDate timestampStart) {
        this.timestampStart = timestampStart;
        return this;
    }

    public void setTimestampStart(LocalDate timestampStart) {
        this.timestampStart = timestampStart;
    }

    public Set<Image> getImages() {
        return images;
    }

    public Scene images(Set<Image> images) {
        this.images = images;
        return this;
    }

    public Scene addImage(Image image) {
        this.images.add(image);
        image.getScenes().add(this);
        return this;
    }

    public Scene removeImage(Image image) {
        this.images.remove(image);
        image.getScenes().remove(this);
        return this;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public Scene chapter(Chapter chapter) {
        this.chapter = chapter;
        return this;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
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
        return id != null && id.equals(((Scene) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
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
