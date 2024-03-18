package com.jher.nid_aux_histoires.service.mapper;

import com.jher.nid_aux_histoires.domain.Book;
import com.jher.nid_aux_histoires.domain.BookStatus;
import com.jher.nid_aux_histoires.domain.Chapter;
import com.jher.nid_aux_histoires.service.dto.BookDTO;
import com.jher.nid_aux_histoires.service.dto.BookStatusDTO;
import com.jher.nid_aux_histoires.service.dto.ChapterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BookStatus} and its DTO {@link BookStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface BookStatusMapper extends EntityMapper<BookStatusDTO, BookStatus> {
    @Mapping(target = "book", source = "book", qualifiedByName = "bookId")
    @Mapping(target = "curentChapter", source = "curentChapter", qualifiedByName = "chapterId")
    BookStatusDTO toDto(BookStatus s);

    @Named("bookId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BookDTO toDtoBookId(Book book);

    @Named("chapterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChapterDTO toDtoChapterId(Chapter chapter);
}
