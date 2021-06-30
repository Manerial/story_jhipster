package com.jher.nid_aux_histoires.service.mapper;


import com.jher.nid_aux_histoires.domain.*;
import com.jher.nid_aux_histoires.service.dto.LibraryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Library} and its DTO {@link LibraryDTO}.
 */
@Mapper(componentModel = "spring", uses = {BookMapper.class, ChapterMapper.class})
public interface LibraryMapper extends EntityMapper<LibraryDTO, Library> {

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "curentChapter.id", target = "curentChapterId")
    LibraryDTO toDto(Library library);

    @Mapping(source = "bookId", target = "book")
    @Mapping(source = "curentChapterId", target = "curentChapter")
    Library toEntity(LibraryDTO libraryDTO);

    default Library fromId(Long id) {
        if (id == null) {
            return null;
        }
        Library library = new Library();
        library.setId(id);
        return library;
    }
}
