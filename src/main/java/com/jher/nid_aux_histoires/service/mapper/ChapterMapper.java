package com.jher.nid_aux_histoires.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jher.nid_aux_histoires.domain.Chapter;
import com.jher.nid_aux_histoires.service.dto.ChapterDTO;

/**
 * Mapper for the entity {@link Chapter} and its DTO {@link ChapterDTO}.
 */
@Mapper(componentModel = "spring", uses = { PartMapper.class })
public interface ChapterMapper extends EntityMapper<ChapterDTO, Chapter> {

	@Mapping(source = "part.id", target = "partId")
	ChapterDTO toDto(Chapter chapter);

	@Mapping(target = "scenes", ignore = true)
	@Mapping(target = "removeScene", ignore = true)
	@Mapping(source = "partId", target = "part")
	Chapter toEntity(ChapterDTO chapterDTO);

	default Chapter fromId(Long id) {
		if (id == null) {
			return null;
		}
		Chapter chapter = new Chapter();
		chapter.setId(id);
		return chapter;
	}
}
