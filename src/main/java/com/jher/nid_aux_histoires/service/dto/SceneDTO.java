package com.jher.nid_aux_histoires.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Scene} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SceneDTO implements Serializable {

    private Long id;

    private String name;

    private Integer number;

    @Lob
    private String text;

    private LocalDate timestampStart;

    private Set<ImageDTO> images = new HashSet<>();

    private ChapterDTO chapter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getTimestampStart() {
        return timestampStart;
    }

    public void setTimestampStart(LocalDate timestampStart) {
        this.timestampStart = timestampStart;
    }

    public Set<ImageDTO> getImages() {
        return images;
    }

    public void setImages(Set<ImageDTO> images) {
        this.images = images;
    }

    public ChapterDTO getChapter() {
        return chapter;
    }

    public void setChapter(ChapterDTO chapter) {
        this.chapter = chapter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SceneDTO)) {
            return false;
        }

        SceneDTO sceneDTO = (SceneDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sceneDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SceneDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", number=" + getNumber() +
            ", text='" + getText() + "'" +
            ", timestampStart='" + getTimestampStart() + "'" +
            ", images=" + getImages() +
            ", chapter=" + getChapter() +
            "}";
    }
}
