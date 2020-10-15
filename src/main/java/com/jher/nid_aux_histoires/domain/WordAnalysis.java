package com.jher.nid_aux_histoires.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A WordAnalysis.
 */
@Entity
@Table(name = "word_analysis")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WordAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "analysis")
    private String analysis;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public WordAnalysis type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public WordAnalysis name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnalysis() {
        return analysis;
    }

    public WordAnalysis analysis(String analysis) {
        this.analysis = analysis;
        return this;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WordAnalysis)) {
            return false;
        }
        return id != null && id.equals(((WordAnalysis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WordAnalysis{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", analysis='" + getAnalysis() + "'" +
            "}";
    }
}
