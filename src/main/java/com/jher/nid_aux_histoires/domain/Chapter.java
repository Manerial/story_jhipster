package com.jher.nid_aux_histoires.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Chapter.
 */
@Entity
@Table(name = "chapter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Chapter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "number")
    private Integer number;

    @OneToMany(mappedBy = "chapter")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Scene> scenes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "chapter_image",
               joinColumns = @JoinColumn(name = "chapter_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
    private Set<Image> images = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "chapters", allowSetters = true)
    private Part part;

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

    public Chapter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Chapter description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumber() {
        return number;
    }

    public Chapter number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Set<Scene> getScenes() {
        return scenes;
    }

    public Chapter scenes(Set<Scene> scenes) {
        this.scenes = scenes;
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

    public void setScenes(Set<Scene> scenes) {
        this.scenes = scenes;
    }

    public Set<Image> getImages() {
        return images;
    }

    public Chapter images(Set<Image> images) {
        this.images = images;
        return this;
    }

    public Chapter addImage(Image image) {
        this.images.add(image);
        image.getChapters().add(this);
        return this;
    }

    public Chapter removeImage(Image image) {
        this.images.remove(image);
        image.getChapters().remove(this);
        return this;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Part getPart() {
        return part;
    }

    public Chapter part(Part part) {
        this.part = part;
        return this;
    }

    public void setPart(Part part) {
        this.part = part;
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
        return id != null && id.equals(((Chapter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
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
