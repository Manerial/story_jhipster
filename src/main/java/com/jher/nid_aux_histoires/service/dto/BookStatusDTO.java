package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.BookStatus} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BookStatusDTO implements Serializable {

    private Long id;

    private Boolean finished;

    private Boolean favorit;

    private BookDTO book;

    private ChapterDTO curentChapter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getFavorit() {
        return favorit;
    }

    public void setFavorit(Boolean favorit) {
        this.favorit = favorit;
    }

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }

    public ChapterDTO getCurentChapter() {
        return curentChapter;
    }

    public void setCurentChapter(ChapterDTO curentChapter) {
        this.curentChapter = curentChapter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookStatusDTO)) {
            return false;
        }

        BookStatusDTO bookStatusDTO = (BookStatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bookStatusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookStatusDTO{" +
            "id=" + getId() +
            ", finished='" + getFinished() + "'" +
            ", favorit='" + getFavorit() + "'" +
            ", book=" + getBook() +
            ", curentChapter=" + getCurentChapter() +
            "}";
    }
}
