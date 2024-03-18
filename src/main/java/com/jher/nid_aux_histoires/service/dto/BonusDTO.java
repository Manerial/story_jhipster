package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Bonus} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BonusDTO implements Serializable {

    private Long id;

    private String name;

    private String extension;

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

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonusDTO)) {
            return false;
        }

        BonusDTO bonusDTO = (BonusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bonusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonusDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", extension='" + getExtension() + "'" +
            "}";
    }
}
