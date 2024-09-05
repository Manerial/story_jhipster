package com.jher.nid_aux_histoires.service.mapper;


import com.jher.nid_aux_histoires.domain.*;
import com.jher.nid_aux_histoires.service.dto.PartDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Part} and its DTO {@link PartDTO}.
 */
@Mapper(componentModel = "spring", uses = {ImageMapper.class, BookMapper.class})
public interface PartMapper extends EntityMapper<PartDTO, Part> {

    @Mapping(source = "book.id", target = "bookId")
    PartDTO toDto(Part part);

    @Mapping(target = "chapters", ignore = true)
    @Mapping(target = "removeChapter", ignore = true)
    @Mapping(target = "removeImage", ignore = true)
    @Mapping(source = "bookId", target = "book")
    Part toEntity(PartDTO partDTO);

    default Part fromId(Long id) {
        if (id == null) {
            return null;
        }
        Part part = new Part();
        part.setId(id);
        return part;
    }
}
