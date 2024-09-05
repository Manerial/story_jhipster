package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Chapter} entity.
 */
public class ChapterDTO implements Serializable {

	private Long id;

	private String name;

	private String description;

	private Integer number;

	private Set<ImageDTO> images = new HashSet<>();

	private Set<SceneDTO> scenes = new HashSet<>();

	private Long partId;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Set<ImageDTO> getImages() {
		return images;
	}

	public void setImages(Set<ImageDTO> images) {
		this.images = images;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ChapterDTO)) {
			return false;
		}

		return id != null && id.equals(((ChapterDTO) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "ChapterDTO{" + "id=" + getId() + ", name='" + getName() + "'" + ", description='" + getDescription()
				+ "'" + ", number=" + getNumber() + ", images='" + getImages() + "'" + ", partId=" + getPartId() + "}";
	}

	public Set<SceneDTO> getScenes() {
		return scenes;
	}

	public void setScenes(Set<SceneDTO> scenes) {
		this.scenes = scenes;
	}
}
