package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.WordAnalysis} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WordAnalysisDTO implements Serializable {

    private Long id;

    private String type;

    private String name;

    private String analysis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WordAnalysisDTO)) {
            return false;
        }

        WordAnalysisDTO wordAnalysisDTO = (WordAnalysisDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, wordAnalysisDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WordAnalysisDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", analysis='" + getAnalysis() + "'" +
            "}";
    }
}
