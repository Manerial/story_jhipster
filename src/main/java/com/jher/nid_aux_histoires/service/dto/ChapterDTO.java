package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.jher.nid_aux_histoires.export.ExportDocx;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Chapter} entity.
 */
public class ChapterDTO implements Serializable, Comparable<ChapterDTO> {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String description;

	private Integer number;

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

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public Set<SceneDTO> getScenes() {
		return scenes;
	}

	public void setScenes(Set<SceneDTO> scenes) {
		this.scenes = new TreeSet<SceneDTO>();
		this.scenes.addAll(scenes);
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
				+ "'" + ", number=" + getNumber() + ", partId=" + getPartId() + "}";
	}

	public String getFormattedText() {
		String text = "";
		for (SceneDTO scene : scenes) {
			text += scene.getText() + ExportDocx.LINE_BREAK_PLACEHOLDER;
		}
		return text.replace("\r\n", ExportDocx.LINE_BREAK_PLACEHOLDER);
	}

	@Override
	public int compareTo(ChapterDTO o) {
		return this.number.compareTo(o.number);
	}
}
