package com.jher.nid_aux_histoires.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BookStatus.
 */
@Entity
@Table(name = "bookStatus")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BookStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "finished")
    private Boolean finished;

    @Column(name = "favorit")
    private Boolean favorit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "parts", "images", "cover", "bookStatuses" }, allowSetters = true)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "scenes", "images", "part", "bookStatuses" }, allowSetters = true)
    private Chapter curentChapter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BookStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public BookStatus finished(Boolean finished) {
        this.setFinished(finished);
        return this;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getFavorit() {
        return this.favorit;
    }

    public BookStatus favorit(Boolean favorit) {
        this.setFavorit(favorit);
        return this;
    }

    public void setFavorit(Boolean favorit) {
        this.favorit = favorit;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BookStatus book(Book book) {
        this.setBook(book);
        return this;
    }

    public Chapter getCurentChapter() {
        return this.curentChapter;
    }

    public void setCurentChapter(Chapter chapter) {
        this.curentChapter = chapter;
    }

    public BookStatus curentChapter(Chapter chapter) {
        this.setCurentChapter(chapter);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookStatus)) {
            return false;
        }
        return getId() != null && getId().equals(((BookStatus) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookStatus{" +
            "id=" + getId() +
            ", finished='" + getFinished() + "'" +
            ", favorit='" + getFavorit() + "'" +
            "}";
    }
}
