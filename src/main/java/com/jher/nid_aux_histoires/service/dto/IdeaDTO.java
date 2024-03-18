package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Idea} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IdeaDTO implements Serializable {

    private Long id;

    private String type;

    private String value;

    private String complement;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IdeaDTO)) {
            return false;
        }

        IdeaDTO ideaDTO = (IdeaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ideaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IdeaDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            ", complement='" + getComplement() + "'" +
            "}";
    }
}
