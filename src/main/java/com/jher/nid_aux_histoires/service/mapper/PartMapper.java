package com.jher.nid_aux_histoires.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jher.nid_aux_histoires.domain.Part;
import com.jher.nid_aux_histoires.service.dto.PartDTO;

/**
 * Mapper for the entity {@link Part} and its DTO {@link PartDTO}.
 */
@Mapper(componentModel = "spring", uses = { BookMapper.class })
public interface PartMapper extends EntityMapper<PartDTO, Part> {

	@Mapping(source = "book.id", target = "bookId")
	PartDTO toDto(Part part);

	@Mapping(target = "chapters", ignore = true)
	@Mapping(target = "removeChapter", ignore = true)
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
