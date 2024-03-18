package com.jher.nid_aux_histoires.service.mapper;


import com.jher.nid_aux_histoires.domain.*;
import com.jher.nid_aux_histoires.service.dto.BookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface BookMapper extends EntityMapper<BookDTO, Book> {

    @Mapping(source = "cover.id", target = "coverId")
    BookDTO toDto(Book book);

    @Mapping(target = "parts", ignore = true)
    @Mapping(target = "removePart", ignore = true)
    @Mapping(target = "removeImage", ignore = true)
    @Mapping(source = "coverId", target = "cover")
    Book toEntity(BookDTO bookDTO);

    default Book fromId(Long id) {
        if (id == null) {
            return null;
        }
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
