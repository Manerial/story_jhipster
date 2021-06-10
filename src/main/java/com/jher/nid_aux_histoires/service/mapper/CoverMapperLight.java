package com.jher.nid_aux_histoires.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jher.nid_aux_histoires.domain.Cover;
import com.jher.nid_aux_histoires.service.dto.CoverDTO;

/**
 * Mapper for the entity {@link Cover} and its DTO {@link CoverDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CoverMapperLight extends EntityMapper<CoverDTO, Cover> {

	@Mapping(target = "picture", ignore = true)
	CoverDTO toDto(Cover coverDTO);

	@Mapping(target = "bookToCovers", ignore = true)
	@Mapping(target = "removeBookToCover", ignore = true)
	Cover toEntity(CoverDTO coverDTO);

	default Cover fromId(Long id) {
		if (id == null) {
			return null;
		}
		Cover cover = new Cover();
		cover.setId(id);
		return cover;
	}
}
